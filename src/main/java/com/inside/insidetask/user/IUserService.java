package com.inside.insidetask.user;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IUserService {
    String saveUser(CredentialsDTO credentialsDTO);

    Map<String, String> revertUserToken(CredentialsDTO credentialsDTO);

}
