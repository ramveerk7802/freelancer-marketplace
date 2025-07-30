package com.rvcode.freelancerMarketplace.freelancer_profile;


import com.rvcode.freelancerMarketplace.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "freelancer")
@Builder
public class FreelancerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> skills;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User users;


}
