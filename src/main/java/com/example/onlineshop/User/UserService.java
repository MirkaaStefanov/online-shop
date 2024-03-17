package com.example.onlineshop.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean ifTwoPasswordsMatch(String pass1, String pass2){
        return pass1.equals(pass2);
    }

}
