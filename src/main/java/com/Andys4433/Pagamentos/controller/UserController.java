package com.Andys4433.Pagamentos.controller;

import java.io.UnsupportedEncodingException;

import com.Andys4433.Pagamentos.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Andys4433.Pagamentos.dto.UserRequest;
import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws UnsupportedEncodingException, MessagingException{
        User user = userRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param ("code") String code){
        if (userService.verify(code)){
            return "Verify_sucess";
        }else{
            return "Verify_fail";
        }
    }
}
