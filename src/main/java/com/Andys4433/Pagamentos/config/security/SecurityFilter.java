package com.Andys4433.Pagamentos.config.security;

import com.Andys4433.Pagamentos.repository.UserRepository;
import com.Andys4433.Pagamentos.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Indica que esta classe é um componente gerenciado pelo Spring.
@Component
// Estende OncePerRequestFilter, garantindo que o filtro seja executado apenas uma vez por solicitação.
public class SecurityFilter extends OncePerRequestFilter {

    // Injeta o serviço de manipulação de tokens.
    @Autowired
    private TokenService tokenService;

    // Injeta o repositório de usuários.
    @Autowired
    private UserRepository userRepository;

    // Método que executa a lógica de filtragem durante cada solicitação.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Recupera o token da solicitação.
        var token = this.recoverToken(request);

        // Se o token existir, realiza a validação do token.
        if (token != null){
            // Validando o token e recuperando o "subject" (geralmente o email do usuário).
            var subject = tokenService.validateToken(token);

            // Busca informações do usuário com base no "subject" (email).
            UserDetails user = userRepository.findByEmail(subject);

            // Cria um objeto de autenticação usando as informações do usuário.
            var authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            // Define o objeto de autenticação no contexto de segurança do Spring.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a cadeia de filtros.
        filterChain.doFilter(request, response);
    }

    // Método para recuperar o token do cabeçalho da solicitação.
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        } else {
            return authHeader.replace("Bearer ", "");
        }
    }
}
/*
 * OncePerRequestFilter: Isso garante que o filtro seja executado apenas uma vez por solicitação, independentemente de quantas vezes a solicitação seja filtrada pela cadeia de filtros.

doFilterInternal: Este é o método onde a lógica de filtragem é implementada. Ele recupera o token da solicitação, valida o token, recupera informações do usuário e configura um objeto de autenticação. Em seguida, define esse objeto no contexto de segurança (SecurityContextHolder).

recoverToken: Um método auxiliar para recuperar o token do cabeçalho de autorização da solicitação.

Injeção de Dependências: Usa a injeção de dependências para obter instâncias de TokenService e UserRepository.
 * 
 */