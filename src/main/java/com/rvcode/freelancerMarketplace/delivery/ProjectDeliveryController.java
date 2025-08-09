package com.rvcode.freelancerMarketplace.delivery;


import com.razorpay.RazorpayException;
import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.delivery.dto.CreateSubmissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}")
@RequiredArgsConstructor
public class ProjectDeliveryController {

    private final ProjectDeliveryService deliveryService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<Void> submitWork(@PathVariable Long projectId, @RequestBody CreateSubmissionRequest request, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        deliveryService.submitWork(projectId,request,customUserDetail);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> approveWork(@PathVariable Long projectId,@AuthenticationPrincipal CustomUserDetail customUserDetail) throws IllegalAccessException, RazorpayException {
        deliveryService.approveWork(projectId,customUserDetail);
        return ResponseEntity.ok().build();
    }
}
