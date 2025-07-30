package com.rvcode.freelancerMarketplace.freelancer_profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<FreelancerProfile,Long> {
}
