package com.rvcode.freelancerMarketplace.register_login;


import com.rvcode.freelancerMarketplace.common.exception.MyCustomException;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.common.jwt_util.JwtService;
import com.rvcode.freelancerMarketplace.common.util.Role;
import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.dto.UserDTO;
import com.rvcode.freelancerMarketplace.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

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
                role = Role.FREELANCER;
                myUser.setFreelancerProfile(new FreelancerProfile());
            }
            myUser.setRole(role);
            User savedUser = userRepository.save(myUser);

            String token = jwtService.generateToken(savedUser);
            return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole().name(),savedUser.getFreelancerProfile(),token);
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
            String token = jwtService.generateToken(dbUser);
            return new UserDTO(dbUser.getId(), dbUser.getUsername(), dbUser.getEmail(),dbUser.getRole().name(), dbUser.getFreelancerProfile() ,token);
        }catch (Exception e) {
            throw  e;
        }
    }



}
