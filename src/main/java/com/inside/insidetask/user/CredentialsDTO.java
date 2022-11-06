package com.inside.insidetask.user;

import lombok.Data;

// класс для работы с телом запроса в методе revertToken, LoginController
@Data
public class CredentialsDTO {
    private String name;

    private String password;
}
