package com.Andys4433.Pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.Andys4433.Pagamentos.entity.User;


// Interface que estende JpaRepository para realizar operações CRUD no banco de dados para a entidade User.
public interface UserRepository extends JpaRepository<User, Long> {

    // Método personalizado para buscar um usuário pelo email. Retorna UserDetails, que é uma interface do Spring Security.
    UserDetails findByEmail(String email);
    
    // Método personalizado para buscar um usuário pelo código de verificação.
    User findByVerificationCode(String verificationCode);
}
/*Extensão do JpaRepository: A interface UserRepository estende JpaRepository<User, Long>. Isso fornece métodos prontos para realizar operações básicas no banco de dados relacionadas à entidade User. Por exemplo, métodos como save, findById, delete, etc.

Método findByEmail: Este é um método personalizado que retorna um UserDetails (uma interface do Spring Security) e é usado para buscar um usuário pelo email. É possível que esse método seja utilizado para carregar detalhes do usuário durante a autenticação.

Método findByVerificationCode: Este é outro método personalizado que retorna um User. Ele é utilizado para buscar um usuário pelo código de verificação, o que pode ser útil durante o processo de verificação do usuário. */