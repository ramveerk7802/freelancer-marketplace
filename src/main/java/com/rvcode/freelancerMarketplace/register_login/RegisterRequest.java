package com.rvcode.freelancerMarketplace.register_login;


import com.rvcode.freelancerMarketplace.freelancer_profile.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;
    private FreelancerProfile freelancerProfile;
}
