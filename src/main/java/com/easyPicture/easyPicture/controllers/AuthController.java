package com.easyPicture.easyPicture.controllers;

import com.easyPicture.easyPicture.dto.LogInDTO;
import com.easyPicture.easyPicture.dto.RegisterDTO;
import com.easyPicture.easyPicture.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(name = "/")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getName());

    @PostMapping(path = {"auth"})
    public String auth(LogInDTO logInDTO, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) {
        log.info("Аутентификация {}", logInDTO.getLogin());
        String hex = authService.auth(logInDTO);
        if (hex != null) {
            setCookie(hex, httpServletResponse);
            log.info("Установка cookie для {}", logInDTO.getLogin());
        } else {
            redirectAttributes.addFlashAttribute("error", "Неверный логин или пароль!");
            return "redirect:/logIn";
        }
        return "redirect:/";
    }

    @PostMapping(path = {"register"})
    public String register(RegisterDTO registerDTO, Model model) {
        if (!registerDTO.getPassword().equals(registerDTO.getRepeatPassword())){
            model.addAttribute("error","Пароли не совпадают!");
            return "registration";
        }
        authService.register(registerDTO);
        return "redirect:/logIn";
    }

    private void setCookie(String hex, HttpServletResponse httpServletResponse) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("token", hex);
        cookie.setMaxAge(172800);
        httpServletResponse.addCookie(cookie);
    }
}
