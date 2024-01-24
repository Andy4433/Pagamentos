package com.Andys4433.Pagamentos.service;

import com.Andys4433.Pagamentos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Método da interface UserDetailsService para carregar detalhes do usuário pelo nome de usuário (email).
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Utiliza o UserRepository para encontrar o usuário pelo email.
        // Se o usuário não for encontrado, lança uma exceção UsernameNotFoundException.
        return userRepository.findByEmail(username);
    }
}

/*Anotação @Service: Indica que esta classe é um componente de serviço gerenciado pelo Spring. Isso significa que pode ser injetado em outras partes do aplicativo.

Implementação da Interface UserDetailsService: A classe implementa a interface UserDetailsService do Spring Security. Essa interface possui um método, loadUserByUsername, que é usado para carregar detalhes do usuário durante o processo de autenticação.

Método loadUserByUsername: Este método é chamado durante a autenticação para carregar detalhes do usuário com base no nome de usuário (que, neste caso, é o email). Utiliza o UserRepository para encontrar o usuário pelo email. Se o usuário não for encontrado, lança uma exceção UsernameNotFoundException. */