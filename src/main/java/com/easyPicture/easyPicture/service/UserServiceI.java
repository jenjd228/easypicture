package com.easyPicture.easyPicture.service;

import com.easyPicture.easyPicture.model.User;

import java.util.List;

public interface UserServiceI {
    User getUserByToken(String token);
    void addImage(String path,String token);
    List getAllUsers();

    User getUserByLogin(String login);
}
