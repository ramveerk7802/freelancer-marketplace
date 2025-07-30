package com.rvcode.freelancerMarketplace.common.exception;

public class UserExistence extends RuntimeException {
    public UserExistence(String message) {
        super(message);
    }
}
