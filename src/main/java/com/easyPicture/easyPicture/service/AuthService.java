package com.easyPicture.easyPicture.service;

import com.easyPicture.easyPicture.dto.LogInDTO;
import com.easyPicture.easyPicture.dto.RegisterDTO;
import com.easyPicture.easyPicture.model.Token;
import com.easyPicture.easyPicture.model.User;
import com.easyPicture.easyPicture.repository.TokenRepository;
import com.easyPicture.easyPicture.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements AuthServiceI {

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(AuthService.class.getName());
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private Token createToken(User user) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String hex = DatatypeConverter.printHexBinary(messageDigest.digest());
        Token token = new Token();
        token.setUser(user);
        token.setToken(hex);
        return token;
    }

    @Override
    public String auth(LogInDTO logInDTO) {
        String hex = null;
        try {
            Optional<User> user = userRepository.findByLoginAndPassword(logInDTO.getLogin(), logInDTO.getPassword());
            if (user.isPresent()) {
                Optional<Token> token = tokenRepository.findByUserId(user.get().getId());
                if (!token.isPresent()) {
                    log.debug("Создание нового токена для {}", logInDTO.getLogin());
                    Token newToken = createToken(user.get());
                    tokenRepository.save(newToken);
                    hex = newToken.getToken();
                } else {
                    hex = token.get().getToken();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return hex;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        User user = new User();
        user.setLogin(registerDTO.getLogin());
        user.setPassword(registerDTO.getPassword());
        userRepository.save(user);
    }
}
