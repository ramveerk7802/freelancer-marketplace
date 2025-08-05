package com.rvcode.freelancerMarketplace.register_login.request_dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
