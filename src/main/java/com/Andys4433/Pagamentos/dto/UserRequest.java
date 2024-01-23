package com.Andys4433.Pagamentos.dto;

import com.Andys4433.Pagamentos.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotNull (message = "Esta sem nome posso colocar sem nome ?") 
    @NotBlank (message = "Esta vazio o nome posso colocar sem nome ?")
    String name, 
    @NotNull (message = "Esta sem sehna posso crir uma senha '123456' ?") 
    @NotBlank (message = "Esta sua senha e vazia posso colocar '123456' ?")
    String password, 
    @NotNull (message = "Esta sem email") 
    @NotBlank (message = "email nao pode ser vazio ?")
    @Email
    String email
    ) {
    
    public  User toModel(){
        return new User(name, email, password);

    }
}
