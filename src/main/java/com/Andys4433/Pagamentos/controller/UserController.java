package com.Andys4433.Pagamentos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Andys4433.Pagamentos.dto.UserRequest;
import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserRequest userRequest){
        User user = userRequest.toModel();
        User userSaved = userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }
}
