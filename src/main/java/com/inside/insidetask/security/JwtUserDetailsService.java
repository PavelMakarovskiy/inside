package com.inside.insidetask.security;

import com.inside.insidetask.user.UserRepository;
import com.inside.insidetask.security.jwt.JwtUserFactory;
import com.inside.insidetask.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User found = userRepository.findUserByName(username);
        if (found == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return JwtUserFactory.create(found);
    }
}
