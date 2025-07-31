package com.rvcode.freelancerMarketplace.freelancer_profile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/freelancer")
public class FreelancerProfileController {

    @Autowired
    private FreelancerProfileService freelancerProfileService;
}
