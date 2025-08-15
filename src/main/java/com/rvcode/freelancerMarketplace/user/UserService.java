package com.rvcode.freelancerMarketplace.user;


import com.rvcode.freelancerMarketplace.common.CustomUserDetail;
import com.rvcode.freelancerMarketplace.common.exception.UserExistence;
import com.rvcode.freelancerMarketplace.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(CustomUserDetail customUserDetail){
        try{
            return userRepository.findByEmail(customUserDetail.getUsername()).orElseThrow(()-> new UserExistence("User not authorized"));

        } catch (Exception e) {
            throw e;
        }
    }
}
