package com.rvcode.freelancerMarketplace.freelancer_profile;


import com.rvcode.freelancerMarketplace.freelancer_profile.dto.FreelancerProfileDTO;
import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelancerProfileService {

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    public FreelancerProfileDTO createOrUpdateProfile(FreelancerProfileDTO dto){
        try {
            FreelancerProfile profile = new FreelancerProfile();
            return FreelancerProfileDTO.builder().build();


        } catch (Exception e) {
            throw e;
        }
    }
}
