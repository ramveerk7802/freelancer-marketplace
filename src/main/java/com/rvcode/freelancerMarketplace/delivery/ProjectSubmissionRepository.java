package com.rvcode.freelancerMarketplace.delivery;

import com.rvcode.freelancerMarketplace.delivery.model.ProjectSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSubmissionRepository extends JpaRepository<ProjectSubmission,Long> {
}
