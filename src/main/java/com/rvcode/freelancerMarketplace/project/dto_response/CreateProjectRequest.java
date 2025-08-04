package com.rvcode.freelancerMarketplace.project.dto_response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProjectRequest {
    private String title;
    private String description;
    private BigDecimal budget;

}


