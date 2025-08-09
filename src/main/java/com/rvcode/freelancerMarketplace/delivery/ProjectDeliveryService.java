package com.rvcode.freelancerMarketplace.delivery;


import com.razorpay.RazorpayException;
import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.common.exception.CustomInformationException;
import com.rvcode.freelancerMarketplace.common.exception.ProjectNotFoundException;
import com.rvcode.freelancerMarketplace.delivery.dto.CreateSubmissionRequest;
import com.rvcode.freelancerMarketplace.delivery.model.ProjectSubmission;
import com.rvcode.freelancerMarketplace.escrow.payment.RazorpayService;
import com.rvcode.freelancerMarketplace.project.ProjectRepository;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectDeliveryService {

    private final ProjectRepository projectRepository;
    private final ProjectSubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final RazorpayService razorpayService;

    @Transactional
    public void submitWork(Long projectId, CreateSubmissionRequest request, CustomUserDetail customUserDetail){
        try{
            User freelancer = userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new CustomInformationException("User not authorized"));
            Project project = projectRepository.findById(projectId).orElseThrow(()->  new ProjectNotFoundException("Project not found"));

            if(project.getHiredFreelancer()==null || !project.getHiredFreelancer().getId().equals(freelancer.getId())){
                throw new CustomInformationException("You are not hired freelancer for this project");
            }

            if(project.getStatus()!= ProjectStatus.IN_PROGRESS){
                throw new CustomInformationException("Work can only be submitted for projects that are in progress.");
            }

            ProjectSubmission submission = ProjectSubmission.builder()
                    .message(request.getMessage())
                    .attachmentUrl(request.getAttachmentUrl())
                    .project(project)
                    .build();

            submissionRepository.save(submission);

            project.setStatus(ProjectStatus.PENDING_APPROVAL);
            projectRepository.save(project);


        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void approveWork(Long projectId, CustomUserDetail customUserDetail) throws IllegalAccessException, RazorpayException {
        try {

            User client = userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new CustomInformationException("User not authorized"));
            Project project = projectRepository.findById(projectId).orElseThrow(()-> new ProjectNotFoundException("Project not found"));

            if(!project.getClient().getId().equals(client.getId())){
                throw new CustomInformationException("You are not the client for this project.");
            }

            if(project.getStatus()!=ProjectStatus.PENDING_APPROVAL){
                throw new IllegalAccessException("Work can only be approved when it is pending approval.");
            }

            project.setStatus(ProjectStatus.COMPLETED);
            projectRepository.save(project);

            razorpayService.releaseEscrowPayment(project);

        }catch (Exception e){
            throw e;
        }
    }
}
