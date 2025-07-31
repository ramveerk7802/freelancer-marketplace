package com.rvcode.freelancerMarketplace.freelancer_profile.model;


import com.rvcode.freelancerMarketplace.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "freelancer")
@NoArgsConstructor
public class FreelancerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "freelancer_profile_id")
    private Long id;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ElementCollection
    private List<String> skills;

    private int yearOfExperience;

    @ElementCollection
    private List<String> certifications;

    private String location;

    private String profilePictureUrl;

    private boolean isVerified;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
