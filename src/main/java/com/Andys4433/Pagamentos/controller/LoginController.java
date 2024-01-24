package com.Andys4433.Pagamentos.controller;

import com.Andys4433.Pagamentos.dto.AuthenticationRequest;
import com.Andys4433.Pagamentos.dto.AuthenticationResponse;
import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        var useramePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        var auth = authenticationManager.authenticate(useramePassword);


        var token = tokenService.generateToken((User) auth.getPrincipal());
        System.out.println("logado");
        return ResponseEntity.ok(new AuthenticationResponse(token));


    }
}


