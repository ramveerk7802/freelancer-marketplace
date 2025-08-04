package com.rvcode.freelancerMarketplace.project;


import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByStatus(ProjectStatus status);
}
