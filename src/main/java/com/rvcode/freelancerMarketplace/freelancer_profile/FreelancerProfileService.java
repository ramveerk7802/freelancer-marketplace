package com.rvcode.freelancerMarketplace.freelancer_profile;


import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.freelancer_profile.dto.UpdateProfileRequest;
import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerProfileService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public FreelancerProfile getMyProfile(String email){
        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new UserExistence("User not found with email: "+ email));
            return user.getFreelancerProfile();



        }catch (Exception e){
            throw e;
        }
    }


    @Transactional
    public FreelancerProfile updateMyProfile(String email, UpdateProfileRequest request) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UserExistence("User not found with email: " + email));
            FreelancerProfile profile = user.getFreelancerProfile();
//            if (profile == null) {
//                throw new MyCustomException("Entity not found exception");
//            }
            profile.setBio(request.getBio());
            profile.setProfilePictureUrl(request.getProfilePictureUrl());
            profile.setSkills(request.getSkills());
            profile.setHeadline(request.getHeadline());
            profile.setCertifications(request.getCertifications());
            profile.setVerified(request.isVerified());
            profile.setYearOfExperience(request.getYearOfExperience());
            profile.setLocation(request.getLocation());
            return profile;

        } catch (Exception e) {
            throw e;
        }


    }
}
