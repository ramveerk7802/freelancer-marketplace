package com.rvcode.freelancerMarketplace.user.dto;


import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String role;
    private FreelancerProfile freelancerProfile;
    private String apiToken;

}
