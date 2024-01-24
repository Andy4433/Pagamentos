package com.Andys4433.Pagamentos.service;

import com.Andys4433.Pagamentos.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Injeta a chave secreta do token do application.properties.
    @Value("${jwt.secret}")
    private String secret;

    // Método para gerar um token JWT com base nas informações do usuário.
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Criação do token com informações como issuer, subject (email do usuário) e tempo de expiração.
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERRO: Token não foi gerado", exception);
        }
    }

    // Método para validar um token JWT e obter o subject (email do usuário) do token.
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Verifica a validade do token, incluindo o issuer.
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido");
        }
    }

    // Método privado para obter a data de expiração do token (5 minutos a partir de agora).
    private Instant expirationDate() {
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
/*Anotação @Service: Indica que esta classe é um componente de serviço gerenciado pelo Spring. Pode ser injetado em outras partes do aplicativo.

Injeção de Propriedade @Value("${jwt.secret}"): Obtém a chave secreta do token do arquivo application.properties usando a anotação @Value.

Método generateToken: Gera um token JWT com base nas informações do usuário. Usa a biblioteca Auth0 JWT para criar o token com informações como emissor, assunto (email do usuário) e tempo de expiração.

Método validateToken: Valida um token JWT e retorna o subject (email do usuário) se o token for válido. Lança uma exceção RuntimeException se o token for inválido.

Método expirationDate: Retorna a data de expiração do token, que é configurada para 5 minutos a partir do momento atual. */