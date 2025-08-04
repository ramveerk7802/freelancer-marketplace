package com.rvcode.freelancerMarketplace.proposal;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.proposal.dto.CreateProposalRequest;
import com.rvcode.freelancerMarketplace.proposal.dto.ProposalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/proposals")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;


    @PostMapping
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<?> submitProposal(@RequestBody CreateProposalRequest request, @PathVariable Long projectId, @AuthenticationPrincipal CustomUserDetail customUserDetail){
        proposalService.createProposal(projectId, request, customUserDetail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<List<ProposalResponse>> getProposals(@PathVariable Long projectId, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        List<ProposalResponse> proposals = proposalService.getProposalsForProject(projectId, customUserDetail);
        return ResponseEntity.ok(proposals);
    }


}
