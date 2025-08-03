package com.rvcode.freelancerMarketplace.freelancer_profile;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.freelancer_profile.dto.UpdateProfileRequest;
import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class FreelancerProfileController {


    private final FreelancerProfileService freelancerProfileService;

    @PreAuthorize("hasRole('FREELANCER')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal CustomUserDetail customUserDetail){
        return ResponseEntity.ok(freelancerProfileService.getMyProfile(customUserDetail));

    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<?> updateMyProfile(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody UpdateProfileRequest request){
        String email = customUserDetail.getUsername();
        return ResponseEntity.ok(freelancerProfileService.updateMyProfile(email,request));

    }
}
