package com.rvcode.freelancerMarketplace.proposal;


import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.proposal.model.Proposal;
import com.rvcode.freelancerMarketplace.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal,Long> {


    List<Proposal> findByProjectId(Long projectId);

    boolean existsByProjectAndFreelancer(Project project, User freelancer);
}
