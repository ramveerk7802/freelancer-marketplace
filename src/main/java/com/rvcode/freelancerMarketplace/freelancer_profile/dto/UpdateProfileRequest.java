package com.rvcode.freelancerMarketplace.freelancer_profile.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UpdateProfileRequest {
    private String headline;
    private String bio;
    private Set<String> skills;
    private int yearOfExperience;
    private Set<String> certifications;
    private String location;
    private String profilePictureUrl;
    private boolean isVerified;

}
