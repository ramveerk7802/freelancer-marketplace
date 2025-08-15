package com.rvcode.freelancerMarketplace.register_login;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String role;
}
