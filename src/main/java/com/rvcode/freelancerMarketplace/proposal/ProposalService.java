package com.rvcode.freelancerMarketplace.proposal;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.common.exception.CustomInformationException;
import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.project.ProjectRepository;
import com.rvcode.freelancerMarketplace.project.dto_response.ProjectResponse;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import com.rvcode.freelancerMarketplace.proposal.dto.CreateProposalRequest;
import com.rvcode.freelancerMarketplace.proposal.dto.ProposalResponse;
import com.rvcode.freelancerMarketplace.proposal.model.Proposal;
import com.rvcode.freelancerMarketplace.proposal.model.ProposalStatus;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createProposal(Long projectId, CreateProposalRequest request, CustomUserDetail customUserDetail){
        try{
            User freelancer = userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new UserExistence("User not Authorized"));
            Project project = projectRepository.findById(projectId).orElseThrow(()-> new MyCustomException("Project not Found with project id"+ projectId));
            if(project.getStatus()!= ProjectStatus.OPEN){
                throw new CustomInformationException("Proposal can only be submitted for open project");
            }

            if(proposalRepository.existsByProjectAndFreelancer(project,freelancer)){
                throw new CustomInformationException("You have already submitted a proposal for this project");
            }
            Proposal proposal = Proposal.builder()
                    .project(project)
                    .freelancer(freelancer)
                    .bidAmount(request.getBidAmount())
                    .coverLetter(request.getCoverLetter())
                    .status(ProposalStatus.PENDING)
                    .build();

            proposalRepository.save(proposal);


        }catch (Exception e){
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public List<ProposalResponse> getProposalsForProject(Long projectId, CustomUserDetail customUserDetail){
        try{
            User client = userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new UserExistence("Client not Authorized"));
            Project project = projectRepository.findById(projectId).orElseThrow(()-> new MyCustomException("Project not found with id "+ projectId));

            if(!project.getClient().getId().equals(client.getId())){
                throw new CustomInformationException("You are not authorized to view proposals for this project.");
            }

            return proposalRepository.findByProjectId(projectId).stream()
                    .map(this::mapToProposalResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw e;
        }
    }

    private ProposalResponse mapToProposalResponse(Proposal proposal) {
        User freelancer = proposal.getFreelancer();
        String headline = (freelancer.getFreelancerProfile() != null) ? freelancer.getFreelancerProfile().getHeadline() : "N/A";

        return new ProposalResponse(
                proposal.getId(),
                proposal.getCoverLetter(),
                proposal.getBidAmount(),
                proposal.getCreatedAt(),
                freelancer.getId(),
                freelancer.getName(),
                headline
        );
    }

}
