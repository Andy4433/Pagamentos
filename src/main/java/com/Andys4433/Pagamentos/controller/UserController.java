package com.Andys4433.Pagamentos.controller;

import java.io.UnsupportedEncodingException;

import com.Andys4433.Pagamentos.dto.AuthenticationRequest;
import com.Andys4433.Pagamentos.dto.AuthenticationResponse;
import com.Andys4433.Pagamentos.dto.UserResponse;
import com.Andys4433.Pagamentos.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

// Indica que esta classe é um controlador REST.
@RestController
// Mapeia todas as solicitações sob o caminho "/user" para este controlador.
@RequestMapping("/user")
public class UserController {

    // Injeta o serviço de usuário.
    @Autowired
    private UserService userService;

    // Injeta o serviço de manipulação de tokens.
    @Autowired
    private TokenService tokenService;

    // Injeta o gerenciador de autenticação do Spring.
    @Autowired
    private AuthenticationManager authenticationManager;

    // Mapeia as solicitações POST para "/user/register".
    @PostMapping("/register")
    // Método que processa solicitações de registro de usuário.
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest)
            throws UnsupportedEncodingException, MessagingException {
        // Converte o objeto UserRequest para um objeto User.
        User user = userRequest.toModel();
        // Registra o usuário usando o serviço de usuário.
        UserResponse userSaved = userService.registerUser(user);
        // Retorna uma resposta bem-sucedida contendo o usuário registrado.
        return ResponseEntity.ok().body(userSaved);
    }

    // Mapeia as solicitações GET para "/user/verify".
    @GetMapping("/verify")
    // Método que verifica um usuário com base no código fornecido.
    public String verifyUser(@Param("code") String code) {
        // Verifica se o código é válido usando o serviço de usuário.
        if (userService.verify(code)) {
            return "Verify_success";
        } else {
            return "Verify_fail";
        }
    }

    // Mapeia as solicitações GET para "/user/teste".
    @GetMapping("/teste")
    // Método de teste que retorna uma string "logado".
    public String test() {
        return "logado";
    }
}
/*
 * @RestController e @RequestMapping: Anotam a classe como um controlador REST e especificam o caminho base para todas as solicitações mapeadas por este controlador.

@Autowired: Utilizado para injetar instâncias de UserService, TokenService, e AuthenticationManager.

@PostMapping("/register"): Mapeia as solicitações HTTP POST para o caminho "/user/register".

@RequestBody e @Valid: Indicam que o corpo da solicitação HTTP deve ser convertido para o objeto UserRequest a partir do JSON, e que a validação do Bean Validation deve ser aplicada.

@GetMapping("/verify"): Mapeia as solicitações HTTP GET para o caminho "/user/verify".

@GetMapping("/teste"): Mapeia as solicitações HTTP GET para o caminho "/user/teste".

userService.registerUser(user): Registra um novo usuário com base nos dados fornecidos.

userService.verify(code): Verifica um usuário com base no código fornecido.

return ResponseEntity.ok().body(userSaved);: Retorna uma resposta HTTP bem-sucedida contendo o usuário registrado.
 */