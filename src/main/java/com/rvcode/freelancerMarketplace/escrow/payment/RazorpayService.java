package com.rvcode.freelancerMarketplace.escrow.payment;


import com.razorpay.*;
import com.rvcode.freelancerMarketplace.common.exception.CustomInformationException;
import com.rvcode.freelancerMarketplace.escrow.payment.dto.PaymentVerificationRequest;
import com.rvcode.freelancerMarketplace.escrow.payment.model.Transaction;
import com.rvcode.freelancerMarketplace.escrow.payment.model.TransactionType;
import com.rvcode.freelancerMarketplace.project.ProjectRepository;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayService {
    private final ProjectRepository projectRepository;
    private final TransactionRepository transactionRepository;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10");

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;


    @Transactional
    public boolean verifyPayment(PaymentVerificationRequest request) throws RazorpayException {
        try{

            Project project = projectRepository.findByPaymentOrderId(request.getRazorpayOrderId()).orElseThrow(()-> new CustomInformationException("Project not found with this order ID"));

            String data = request.getRazorpayOrderId()+"|"+request.getRazorpayPaymentId();
            String expectedSignature = Utils.getHash(data,razorpayKeySecret);

            if(expectedSignature.equals(request.getRazorpaySignature())){
                project.setStatus(ProjectStatus.IN_PROGRESS);
                projectRepository.save(project);
                return true;
            }
            else{
                return false;
            }


        }catch (Exception e){
            throw e;

        }

    }


    @Transactional
    public void releaseEscrowPayment(Project project) throws RazorpayException {

        try {
            //  find the original payment id from the order
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
            Order order = razorpayClient.orders.fetch(project.getPaymentOrderId());

            String paymentId = order.get("receipt");

            // calculate amount
            Payment payment = razorpayClient.payments.fetch(paymentId);
            BigDecimal totalAmount = project.getBudget();
            BigDecimal commissionAmount = totalAmount.multiply(COMMISSION_RATE);
            BigDecimal freelancerPayoutAmount = totalAmount.subtract(commissionAmount);

            // This is a conceptual representation. The actual implementation depends heavily
            // on having the freelancer's linked account ID from Razorpay's onboarding.
            // String freelancerAccountId = project.getHiredFreelancer().getRazorpayAccountId();

            JSONObject transferRequest = new JSONObject();
            JSONArray transfers = new JSONArray();

            // Transfer to Freelancer
            JSONObject freelancerTransfer = new JSONObject();
            freelancerTransfer.put("account", "acc_FREELANCER_ACCOUNT_ID"); // Replace with actual freelancer account ID
            freelancerTransfer.put("amount", freelancerPayoutAmount.multiply(new BigDecimal("100")).intValue());
            freelancerTransfer.put("currency", "INR");
            transfers.put(freelancerTransfer);

            // Transfer to Platform (Commission)
            JSONObject commissionTransfer = new JSONObject();
            commissionTransfer.put("account", "acc_PLATFORM_ACCOUNT_ID"); // Replace with your platform's account ID
            commissionTransfer.put("amount", commissionAmount.multiply(new BigDecimal("100")).intValue());
            commissionTransfer.put("currency", "INR");
            transfers.put(commissionTransfer);

            transferRequest.put("transfers", transfers);

            // Transfer request

            Transfer transfer = (Transfer) razorpayClient.payments.transfer(paymentId,transferRequest);

            // Log transactions to our database (simplified)
            // You would get the individual transfer IDs from the 'transfer' response object

            Transaction payoutLog = Transaction.builder()
                    .project(project)
                    .user(project.getHiredFreelancer())
                    .amount(freelancerPayoutAmount)
                    .type(TransactionType.PAYOUT)
                    .paymentGatewayTransferId("trf_"+System.currentTimeMillis())
                    .build();
            transactionRepository.save(payoutLog);


        } catch (Exception  e) {
            throw e;
        }
    }
}
