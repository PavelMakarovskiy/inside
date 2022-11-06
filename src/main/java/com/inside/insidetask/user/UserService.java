package com.inside.insidetask.user;

import com.inside.insidetask.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    // метод для кодировки пароля, сохранения пользователя
    @Override
    public String saveUser(CredentialsDTO credentialsDTO) {
        if (credentialsDTO.getName() != null && credentialsDTO.getPassword() != null) {
            User user = new User();
            user.setName(credentialsDTO.getName());
            user.setPassword(passwordEncoder.encode(credentialsDTO.getPassword()));
            User savedUser = userRepository.save(user);
            return "User " + savedUser.getName() + " added successfully.";
        } else {
            throw new IllegalArgumentException("CredentialsDTO has illegal parameter");
        }
    }

    // метод для возврата токена
    @Override
    public Map<String, String> revertUserToken(CredentialsDTO credentialsDTO) {
        try {
            String name = credentialsDTO.getName();
            String password = credentialsDTO.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
            User user = userRepository.findUserByName(name);
            if (user == null) {
                throw new UsernameNotFoundException("User with name: " + name + " not found.");
            }
            String token = jwtTokenProvider.createToken(name);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            return tokenMap;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
