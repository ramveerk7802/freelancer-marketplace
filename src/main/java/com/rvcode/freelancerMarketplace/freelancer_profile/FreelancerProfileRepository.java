package com.rvcode.freelancerMarketplace.freelancer_profile;


import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile,Long> {

}
