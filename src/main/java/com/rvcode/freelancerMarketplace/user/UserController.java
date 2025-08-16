package com.rvcode.freelancerMarketplace.user;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@AuthenticationPrincipal CustomUserDetail customUserDetail){


        return ResponseEntity.ok(userService.getUser(customUserDetail));
    }

}
