package com.easyPicture.easyPicture.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String login;

    private String password;

    private String repeatPassword;
}
