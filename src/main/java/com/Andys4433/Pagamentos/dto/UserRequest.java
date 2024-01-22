package com.Andys4433.Pagamentos.dto;

import com.Andys4433.Pagamentos.entity.User;

public record UserRequest(String name, String password, String email) {
    
    public  User toModel(){
        return new User(name, email, password);

    }
}
