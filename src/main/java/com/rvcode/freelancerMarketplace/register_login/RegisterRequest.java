package com.rvcode.freelancerMarketplace.register_login;


import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String role;
}
