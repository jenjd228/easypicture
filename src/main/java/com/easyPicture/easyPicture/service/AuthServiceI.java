package com.easyPicture.easyPicture.service;

import com.easyPicture.easyPicture.dto.LogInDTO;
import com.easyPicture.easyPicture.dto.RegisterDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

public interface AuthServiceI {
    String auth(LogInDTO logInDTO);
    void register(RegisterDTO registerDTO);
}
