package com.Andys4433.Pagamentos.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// Anotação indicando que esta classe é uma entidade JPA.
@Entity(name = "users")
// Define o nome da tabela no banco de dados.
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    // Identificador único gerado automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String verificationCode;
    private boolean enabled;

    // Construtor que inicializa todos os campos.
    public User(Long id, String name, String email, String password, String verificationCode, boolean enabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.enabled = enabled;
    }

    // Construtor utilizado para criar usuários sem especificar o ID ou código de verificação.
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Construtor vazio necessário para JPA.
    public User() {}

    // Métodos da interface UserDetails:

    // Retorna as autoridades concedidas ao usuário.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Você pode implementar isso para retornar as autoridades do usuário se necessário.
    }

    // Retorna o nome de usuário (que é o email neste caso).
    @Override
    public String getUsername() {
        return email;
    }

    // Indica se a conta do usuário não expirou.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indica se a conta do usuário não está bloqueada.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indica se as credenciais do usuário (senha) não expiraram.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indica se o usuário está habilitado.
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
/*Implementação da Interface UserDetails: A classe User implementa a interface UserDetails, fornecendo métodos necessários para a autenticação e autorização do Spring Security.

Anotações JPA (@Entity, @Table, @Id, @GeneratedValue): Usadas para mapear a classe para uma tabela no banco de dados e especificar detalhes sobre a geração de chaves primárias.

Métodos da Interface UserDetails: Os métodos getAuthorities(), getUsername(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), e isEnabled() são implementados conforme exigido pela interface UserDetails.

Campos da Entidade: Campos como name, email, password, verificationCode, e enabled representam atributos da entidade User */