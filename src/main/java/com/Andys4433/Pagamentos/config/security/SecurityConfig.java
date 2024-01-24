package com.Andys4433.Pagamentos.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



// Indica que esta classe é uma configuração de Spring.
@Configuration
// Ativa a configuração de segurança do Spring.
@EnableWebSecurity
public class SecurityConfig {

    // Injeta o SecurityFilter personalizado.
    @Autowired
    private SecurityFilter securityFilter;

    // Configuração principal do Spring Security.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
            // Desabilita a proteção CSRF (Cross-Site Request Forgery).
            .csrf(csrf -> csrf.disable())
            // Configura a política de gerenciamento de sessão como STATELESS, indicando que não deve ser usada uma sessão.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configura as autorizações para os endpoints.
            .authorizeHttpRequests( authorize -> authorize
                // Permite a todos acessar o endpoint de registro de usuário.
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                // Permite a todos acessar o endpoint de verificação de usuário.
                .requestMatchers(HttpMethod.GET, "/user/verify").permitAll()
                // Permite a todos acessar o endpoint de login.
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // Exige autenticação para qualquer outro endpoint.
                .anyRequest().authenticated()
            )
            // Adiciona o filtro de segurança personalizado antes do filtro padrão de autenticação de usuário e senha.
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // Configura e fornece o gerenciador de autenticação do Spring Security.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Fornece um codificador de senha para ser usado na autenticação.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
/*
 * @Configuration e @EnableWebSecurity: Essas anotações indicam que esta classe é uma configuração do Spring e ativa a configuração de segurança da web do Spring.

SecurityFilter e SecurityFilterChain: O SecurityFilter é um filtro personalizado que você criou para manipular a lógica de autenticação e autorização. SecurityFilterChain configura a lógica de segurança para a aplicação.

Configuração do HttpSecurity: Aqui você está usando o HttpSecurity para configurar várias políticas de segurança, como desabilitar CSRF, configurar a política de sessão como STATELESS, definir permissões para diferentes endpoints e adicionar filtros de segurança.

Endpoints permitidos e exigindo autenticação: Alguns endpoints, como registro de usuário, verificação de usuário e login, são permitidos para todos (permitAll()), enquanto outros exigem autenticação.

AuthenticationManager e PasswordEncoder: Esses beans são fornecidos para que o Spring Security possa gerenciar autenticação e codificação de senha, respectivamente.
 */