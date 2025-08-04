package com.rvcode.freelancerMarketplace.proposal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponse {

    private Long id;
    private String coverLetter;
    private BigDecimal bidAmount;
    private LocalDateTime createdAt;
    private Long freelancerId;
    private String freelancerName;
    private String freelancerHeadline;
}
