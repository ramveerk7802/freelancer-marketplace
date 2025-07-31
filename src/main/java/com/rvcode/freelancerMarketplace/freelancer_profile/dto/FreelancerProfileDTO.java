package com.rvcode.freelancerMarketplace.freelancer_profile.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FreelancerProfileDTO {
    private Long profileId;
    private Long userId;
    private String headline;
    private String bio;
    private List<String> skills;
    private int yearsOfExperience;
    private List<String> certifications;
    private String portfolio;
    private String location;
    private String profilePictureUrl;
    private boolean isVerified;

}
