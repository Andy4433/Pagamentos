package com.Andys4433.Pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.Andys4433.Pagamentos.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);;
    
}
