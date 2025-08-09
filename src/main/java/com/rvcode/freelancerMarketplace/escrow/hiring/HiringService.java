package com.rvcode.freelancerMarketplace.escrow.hiring;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.common.exception.CustomInformationException;
import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.escrow.hiring.dto.HiringResponse;
import com.rvcode.freelancerMarketplace.project.ProjectRepository;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import com.rvcode.freelancerMarketplace.proposal.ProposalRepository;
import com.rvcode.freelancerMarketplace.proposal.model.Proposal;
import com.rvcode.freelancerMarketplace.proposal.model.ProposalStatus;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HiringService {
    private final ProjectRepository projectRepository;
    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;



    @Transactional
    public HiringResponse hiredFreelancerAndCreateOrder(Long projectId, Long proposalId, CustomUserDetail customUserDetail) throws RazorpayException {
        try{
            User client = userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new UserExistence("User not Authorized"));
            Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(()-> new CustomInformationException("Proposal not found"));
            Project project = projectRepository.findById(projectId).orElseThrow(()-> new CustomInformationException("Project not Found"));

            if(!project.getClient().getId().equals(client.getId()))
                throw new CustomInformationException("You are not owner of this Project");
            if(project.getStatus()!= ProjectStatus.OPEN)
                throw new CustomInformationException("Project is not open for hiring");

            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);

            JSONObject orderRequest = new JSONObject();

            BigDecimal amountInPaise = proposal.getBidAmount().multiply(new BigDecimal(100));
            orderRequest.put("amount",amountInPaise);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt","receipt_project_"+project.getId());

            Order order = razorpayClient.orders.create(orderRequest);
            String orderId = order.get("id");

            project.setHiredFreelancer(proposal.getFreelancer());
            project.setStatus(ProjectStatus.AWAITING_PAYMENT);
            project.setPaymentOrderId(orderId);

            proposal.setStatus(ProposalStatus.ACCEPTED);
            List<Proposal> otherProposal = proposalRepository.findByProjectId(projectId);
            otherProposal.stream()
                    .filter(p->p.getId().equals(proposalId))
                    .forEach(p->p.setStatus(ProposalStatus.REJECTED));

            projectRepository.save(project);
            proposalRepository.saveAll(otherProposal);

            return HiringResponse.builder()
                    .razorpayOrderId(orderId)
                    .amount(proposal.getBidAmount())
                    .currency("INR")
                    .razorpayKeyId(razorpayKeyId)
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }

}
