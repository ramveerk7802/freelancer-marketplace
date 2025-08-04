package com.rvcode.freelancerMarketplace.proposal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProposalRequest{
    private String coverLetter;
    private BigDecimal bidAmount;

}

