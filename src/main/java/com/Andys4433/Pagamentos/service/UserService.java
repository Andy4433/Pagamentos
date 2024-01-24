package com.Andys4433.Pagamentos.service;

import java.io.UnsupportedEncodingException;

import com.Andys4433.Pagamentos.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.Andys4433.Pagamentos.entity.User;
import com.Andys4433.Pagamentos.repository.UserRepository;
import com.Andys4433.Pagamentos.util.RandomString;

import jakarta.mail.MessagingException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    // Método para registrar um novo usuário
    public UserResponse registerUser(User user) throws UnsupportedEncodingException, MessagingException {
        // Verifica se o e-mail já está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Este email já está registrado");
        } else {
            // Codifica a senha antes de salvar no banco de dados
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // Gera um código de verificação aleatório
            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            // Salva o usuário no banco de dados
            User savedUser = userRepository.save(user);

            // Cria a resposta do usuário para retornar ao cliente
            UserResponse userResponse = new UserResponse(
                savedUser.getId(), 
                savedUser.getName(), 
                savedUser.getEmail(),
                savedUser.getPassword()
            );

            // Envia um e-mail de verificação
            mailService.sendVerificationEmail(user);

            return userResponse;
        }
    }

    // Método para verificar o código de verificação e ativar o usuário
    public boolean verify(String verificationCode) {
        // Procura um usuário pelo código de verificação
        User user = userRepository.findByVerificationCode(verificationCode);

        // Verifica se o usuário não existe ou já está ativado
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            // Atualiza o usuário para estar ativado
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}
/*Anotação @Service: Indica que esta classe é um componente de serviço gerenciado pelo Spring.

Injeção de Dependência: A classe utiliza a injeção de dependência para obter instâncias de UserRepository, PasswordEncoder e MailService.

Método registerUser: Registra um novo usuário, verificando se o e-mail já está registrado, codificando a senha, gerando um código de verificação aleatório e enviando um e-mail de verificação.

Método verify: Verifica o código de verificação e ativa o usuário se o código for válido e o usuário ainda não estiver ativado. Retorna verdadeiro se a verificação for bem-sucedida. */