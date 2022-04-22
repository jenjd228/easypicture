package com.easyPicture.easyPicture.controllers;

import com.easyPicture.easyPicture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = {"/"})
public class PostController {

    @Autowired
    PostController(UserService userService){
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping(path = {"addImage"})
    public String addImage(@RequestParam String path, @CookieValue(value = "token", required = false) String token){
        if (token == null) {
            return "redirect:/logIn";
        }
        userService.addImage(path,token);
        return "redirect:/";
    }
}
