package com.inside.insidetask.user;

import com.inside.insidetask.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    private UserRepository userRepository;

    private JwtTokenProvider jwtTokenProvider;

    private IUserService userService;

    @Autowired
    public LoginController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository,
                           IUserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // энд поинт для проверки пароля, пользователя и для возврата токена.
    @PostMapping("/login")
    public Map<String, String> revertToken(@RequestBody CredentialsDTO credentialsDTO) {
        return userService.revertUserToken(credentialsDTO);
    }

}
