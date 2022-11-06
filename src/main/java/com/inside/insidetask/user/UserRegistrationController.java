package com.inside.insidetask.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {
    private UserRepository userRepository;

    private IUserService userService;

    @Autowired
    public UserRegistrationController(UserRepository userRepository, IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // Энд поинт для получения пользователя, пароля, дальнейшей кодировки пароля и добавления пользователя в БД.
    @PostMapping("/auth")
    public String revertSavedUserStatus(@RequestBody CredentialsDTO credentialsDTO) {
        return userService.saveUser(credentialsDTO);
    }
}
