package com.rvcode.freelancerMarketplace.register_login;


import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.common.jwt_util.JwtService;
import com.rvcode.freelancerMarketplace.common.util.Role;
import com.rvcode.freelancerMarketplace.freelancer_profile.FreelancerProfileRepository;
import com.rvcode.freelancerMarketplace.freelancer_profile.model.FreelancerProfile;
import com.rvcode.freelancerMarketplace.register_login.request_dto.LoginRequest;
import com.rvcode.freelancerMarketplace.register_login.request_dto.RegisterRequest;
import com.rvcode.freelancerMarketplace.user.UserRepository;
import com.rvcode.freelancerMarketplace.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest){
        try {
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new UserExistence("Email already register, Please use another email");
            }
            User myUser = new User();
            myUser.setEmail(registerRequest.getEmail());
            myUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            myUser.setName(registerRequest.getName());

            // if user is register as Freelancer
            if(registerRequest.getRole().equalsIgnoreCase(Role.FREELANCER.name())){
                myUser.setRole(Role.FREELANCER);
                FreelancerProfile profile = new FreelancerProfile();
                profile.setUser(myUser);
                myUser.setFreelancerProfile(profile);
            }else
                myUser.setRole(Role.CLIENT);

            User savedUser = userRepository.save(myUser);

            String token = jwtService.generateToken(savedUser);
            return AuthResponse.builder()
                    .token(token)
                    .build();
//            return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole().name(),savedUser.getFreelancerProfile(),token);
        }catch (Exception e){
            throw e;
        }

    }


    public AuthResponse loginUser(LoginRequest loginRequest){
        try {
            if(!userRepository.existsByEmail(loginRequest.getEmail())){
                throw new UserExistence("User register first then login\nEnter valid credential");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            User dbUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UserExistence("Enter valid credential"));

            String token = jwtService.generateToken(dbUser);
            return AuthResponse.builder().token(token).role(dbUser.getRole().name()).build();
//            return new UserDTO(dbUser.getId(), dbUser.getUsername(), dbUser.getEmail(),dbUser.getRole().name(), dbUser.getFreelancerProfile() ,token);
        }catch (Exception e) {
            throw  e;
        }
    }



}
