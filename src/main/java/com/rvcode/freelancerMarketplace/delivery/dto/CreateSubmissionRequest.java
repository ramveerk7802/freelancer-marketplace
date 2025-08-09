package com.rvcode.freelancerMarketplace.delivery.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSubmissionRequest {
    private String message;
    private String attachmentUrl;
}
