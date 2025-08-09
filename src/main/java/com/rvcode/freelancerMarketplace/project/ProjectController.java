package com.rvcode.freelancerMarketplace.project;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.project.dto_response.CreateProjectRequest;
import com.rvcode.freelancerMarketplace.project.dto_response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;


    @GetMapping("/health")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Project controller health okk"));
    }


    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProjectResponse> createProject(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody CreateProjectRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(customUserDetail,request));

    }


    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllOpenProject(){
        return ResponseEntity.ok(projectService.findOpenProject());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findProjectById(id));
    }
}
