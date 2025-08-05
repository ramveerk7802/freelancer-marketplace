package com.rvcode.freelancerMarketplace.escrow.payment;


import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.rvcode.freelancerMarketplace.common.exception.CustomInformationException;
import com.rvcode.freelancerMarketplace.escrow.payment.dto.PaymentVerificationRequest;
import com.rvcode.freelancerMarketplace.project.ProjectRepository;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RazorpayService {
    private final ProjectRepository projectRepository;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;


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
}
