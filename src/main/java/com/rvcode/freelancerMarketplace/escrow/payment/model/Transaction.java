package com.rvcode.freelancerMarketplace.escrow.payment.model;


import com.rvcode.freelancerMarketplace.project.model.Project;
import com.rvcode.freelancerMarketplace.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;        // This user who receiving the fund (Freelancer) or platform


    @Column(unique = true)
    private String paymentGatewayTransferId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
