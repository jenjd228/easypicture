package com.easyPicture.easyPicture.service;

import com.easyPicture.easyPicture.model.Image;
import com.easyPicture.easyPicture.model.Token;
import com.easyPicture.easyPicture.model.User;
import com.easyPicture.easyPicture.repository.ImageRepository;
import com.easyPicture.easyPicture.repository.TokenRepository;
import com.easyPicture.easyPicture.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceI {

    UserService(TokenRepository tokenRepository, UserRepository userRepository, ImageRepository imageRepository){
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }
    private final ImageRepository imageRepository;
    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    @Override
    public User getUserByToken(String token) {
        return tokenRepository.findByToken(token).get().getUser();
    }

    @Override
    public void addImage(String path, String token) {
        Optional<Token> tokenFromBd = tokenRepository.findByToken(token);
        if (tokenFromBd.isPresent()){
            Image image = new Image();
            image.setSrc(path);
            User user = tokenFromBd.get().getUser();
            image.setUser(user);
            imageRepository.save(image);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }
}
