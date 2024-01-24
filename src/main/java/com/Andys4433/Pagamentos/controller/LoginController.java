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

// Indica que esta classe é um controlador REST.
@RestController
// Mapeia todas as solicitações sob o caminho "/auth" para este controlador.
@RequestMapping("/auth")
public class LoginController {

    // Injeta o serviço de manipulação de tokens.
    @Autowired
    private TokenService tokenService;

    // Injeta o gerenciador de autenticação do Spring.
    @Autowired
    private AuthenticationManager authenticationManager;

    // Mapeia as solicitações POST para "/auth/login".
    @PostMapping("/login")
    // Método que processa solicitações de login.
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        // Cria um objeto de autenticação usando as credenciais fornecidas na solicitação.
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        // Realiza a autenticação usando o gerenciador de autenticação do Spring.
        var auth = authenticationManager.authenticate(usernamePassword);

        // Gera um token com base nas informações do usuário autenticado.
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // Imprime uma mensagem indicando que o usuário está logado (pode ser útil apenas para fins de depuração).
        System.out.println("logado");

        // Retorna uma resposta bem-sucedida contendo o token gerado.
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
/*
 * @RestController e @RequestMapping: Anotam a classe como um controlador REST e especificam o caminho base para todas as solicitações mapeadas por este controlador.

@Autowired: Utilizado para injetar instâncias de TokenService e AuthenticationManager.

@PostMapping("/login"): Mapeia as solicitações HTTP POST para o caminho "/auth/login".

@RequestBody: Indica que o corpo da solicitação HTTP deve ser convertido para o objeto AuthenticationRequest a partir do JSON.

AuthenticationManager: Usado para autenticar um usuário com base nas credenciais fornecidas.

Geração de Token: Após a autenticação bem-sucedida, um token é gerado usando o TokenService.

AuthenticationResponse: Um objeto que contém o token e é retornado como parte da resposta HTTP.

ResponseEntity.ok(new AuthenticationResponse(token)): Constrói uma resposta HTTP bem-sucedida contendo o objeto AuthenticationResponse com o token.
 */

