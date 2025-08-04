package com.rvcode.freelancerMarketplace.project.dto_response;


import com.rvcode.freelancerMarketplace.project.model.ProjectStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal budget;
    private ProjectStatus status;
    private Long clientId;
    private String clientName;
    private LocalDateTime createdAt;
}
