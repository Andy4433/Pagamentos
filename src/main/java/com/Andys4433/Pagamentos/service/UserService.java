package com.Andys4433.Pagamentos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.repository.UserRepository;
import com.Andys4433.Pagamentos.util.RandomString;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder PasswordEncoder;

    public User registerUser(User user){
        if (userRepository.findByEmail(user.getEmail())!=null) {
            throw new RuntimeException("Que peninha esse email ja esta na area");
            
        }else{
            String encodedPassword = PasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);
            User savedUser = userRepository.save(user);
            return savedUser;
        }
    }
}
