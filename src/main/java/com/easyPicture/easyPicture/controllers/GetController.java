package com.easyPicture.easyPicture.controllers;

import com.easyPicture.easyPicture.model.User;
import com.easyPicture.easyPicture.service.AuthService;
import com.easyPicture.easyPicture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/")
public class GetController {

    GetController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }
    private final AuthService authService;
    private final UserService userService;

    @GetMapping
    public String index(@CookieValue(value = "token", required = false) String token, Model model) {
        if (token == null) {

            return "redirect:/logIn";
        }
        model.addAttribute("user",userService.getUserByToken(token));
        model.addAttribute("allUsers",userService.getAllUsers());
        return "index";
    }

    @GetMapping(path = {"indexForeign/{userLogin}"})
    public String indexForeign(@PathVariable(name = "userLogin") String userLogin,Model model){
        User user = userService.getUserByLogin(userLogin);
        if (user == null){
         return "redirect:/";
        }
        model.addAttribute("user",user);
        model.addAttribute("allUsers",userService.getAllUsers());
        return "indexForeign";
    }

    @GetMapping(path = {"logIn"})
    public String logIn() {
        return "logIn";
    }

    @GetMapping(path = {"logOut"})
    public String logOut(HttpServletResponse httpServletResponse){
        deleteCookie(httpServletResponse);
        return "redirect:/logIn";
    }

    private void deleteCookie(HttpServletResponse httpServletResponse) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("token", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    @GetMapping(path = {"register"})
    public String register() {
        return "registration";
    }

    @GetMapping(path = {"add"})
    public String add(@CookieValue(value = "token", required = false) String token, Model model){
        if (token == null) {
            return "redirect:/logIn";
        }
        model.addAttribute("user",userService.getUserByToken(token));
        return "add";
    }
}
