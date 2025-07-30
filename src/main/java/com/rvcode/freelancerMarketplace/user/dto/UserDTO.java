package com.rvcode.freelancerMarketplace.user.dto;


import com.rvcode.freelancerMarketplace.freelancer_profile.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String role;
    private FreelancerProfile freelancerProfile;
}
