package com.rvcode.freelancerMarketplace.register_login;


import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.common.util.Role;
import com.rvcode.freelancerMarketplace.freelancer_profile.FreelancerProfile;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.dto.UserDTO;
import com.rvcode.freelancerMarketplace.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(RegisterRequest registerRequest){
        try {
            if(userRepository.existsByUsername(registerRequest.getUsername()))
                throw new UserExistence("Username already register with "+ registerRequest.getUsername());
            else if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new UserExistence("Email already register, Please use another email");
            }
            User myUser = new User();
            myUser.setUsername(registerRequest.getUsername());
            myUser.setEmail(registerRequest.getEmail());
            myUser.setPassword(registerRequest.getPassword());
            Role role = Role.CLIENT;

            // if user is register as Freelancer
            if(registerRequest.getRole().equalsIgnoreCase(Role.FREELANCER.name())){
                if(registerRequest.getFreelancerProfile()==null)
                    throw new MyCustomException("Enter the Freelancer profile detail");
                role = Role.FREELANCER;
                FreelancerProfile profile = FreelancerProfile.builder()
                        .skills(registerRequest.getFreelancerProfile().getSkills())
                        .users(myUser)
                        .build();
            }
            myUser.setRole(role);
            User savedUser = userRepository.save(myUser);
            return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole().name(),savedUser.getFreelancerProfile());
        }catch (Exception e){
            throw e;
        }

    }


    public UserDTO loginUser(LoginRequest loginRequest){
        try {
            if(!userRepository.existsByUsername(loginRequest.getUsername())){
                throw new UserExistence("User register first then login\nEnter valid credential");
            }
            User dbUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new UserExistence("Enter valid credential"));

            if(!dbUser.getPassword().equals(loginRequest.getPassword()))
                throw new UserExistence("Enter the valid credential");
            return new UserDTO(dbUser.getId(), dbUser.getUsername(), dbUser.getEmail(),dbUser.getRole().name(), dbUser.getFreelancerProfile() );
        }catch (Exception e) {
            throw  e;
        }
    }



}
