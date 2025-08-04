package com.rvcode.freelancerMarketplace.project;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.project.dto_response.CreateProjectRequest;
import com.rvcode.freelancerMarketplace.project.dto_response.ProjectResponse;
import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    @Transactional
    public ProjectResponse createProject(CustomUserDetail customUserDetail, CreateProjectRequest request){
        try {
            String email = customUserDetail.getUsername();
            User client = userRepository.findByEmail(email).orElseThrow(()-> new UserExistence("User not Authorized/ Not found with email"));

            Project project = Project.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .budget(request.getBudget())
                    .status(ProjectStatus.OPEN)
                    .client(client)
                    .build();
            Project saveProject = projectRepository.save(project);
            return mapToProjectResponse(saveProject);

        } catch (Exception e) {
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public List<ProjectResponse> findOpenProject(){
        try{
            return projectRepository.findByStatus(ProjectStatus.OPEN)
                    .stream()
                    .map(this::mapToProjectResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public ProjectResponse findProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::mapToProjectResponse)
                .orElseThrow(() -> new MyCustomException("Project not found with id: " + id));
    }




    private ProjectResponse mapToProjectResponse(Project project){
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .budget(project.getBudget())
                .status(project.getStatus())
                .clientId(project.getClient().getId())
                .clientName(project.getClient().getName())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
