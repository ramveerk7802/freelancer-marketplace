package com.rvcode.freelancerMarketplace.freelancer_profile.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rvcode.freelancerMarketplace.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "freelancer")
@NoArgsConstructor
public class FreelancerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "freelancer_skills", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skill")
    private Set<String> skills;

    private int yearOfExperience;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "freelancer_certifications", joinColumns = @JoinColumn(name = "profile_id")) // Use a different table name
    @Column(name = "certification")
    private Set<String> certifications;

    private String location;

    private String profilePictureUrl;

    private boolean isVerified;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


}
