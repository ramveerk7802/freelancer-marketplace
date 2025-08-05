package com.rvcode.freelancerMarketplace.escrow.hiring;


import com.razorpay.RazorpayException;
import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.escrow.hiring.dto.HiringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/proposals/{proposalId}")
public class HiringController {

    private final HiringService hiringService;

    @PostMapping("/hire")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<HiringResponse> hireFreelancer(@PathVariable Long projectId, @PathVariable Long proposalId, @AuthenticationPrincipal CustomUserDetail customUserDetail) throws RazorpayException {
        HiringResponse response = hiringService.hiredFreelancerAndCreateOrder(projectId,proposalId,customUserDetail);
        return ResponseEntity.ok(response);

    }
}
