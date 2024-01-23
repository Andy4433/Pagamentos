package com.Andys4433.Pagamentos.service;

import java.io.UnsupportedEncodingException;

import com.Andys4433.Pagamentos.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.repository.UserRepository;
import com.Andys4433.Pagamentos.util.RandomString;

import jakarta.mail.MessagingException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private MailService mailService;


    public UserResponse registerUser(User user) throws UnsupportedEncodingException, MessagingException{
        if (userRepository.findByEmail(user.getEmail())!=null) {
            throw new RuntimeException("Que peninha esse email ja esta na area");
            
        }else{
            String encodedPassword = PasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);
            User savedUser = userRepository.save(user);
            UserResponse userResponse = new UserResponse(
                savedUser.getId(), 
                savedUser.getName(), 
                savedUser.getEmail(),
                 savedUser.getPassword()
            );
            mailService.sendVerificationEmail(user);
            return userResponse;
        }
    }
    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()){
            return false;
        }else{
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}
