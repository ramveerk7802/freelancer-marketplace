package com.rvcode.freelancerMarketplace.project.model;

public enum ProjectStatus {
    OPEN,               // Newly created, freelancers can apply
    AWAITING_PAYMENT,   // Client has hired, payment is pending
    IN_PROGRESS,        // Escrow funded, work can begin
    PENDING_APPROVAL,   // Work submitted, client to review
    COMPLETED,          // Client approved, payment released
    DISPUTED            // A conflict has been raised
}
