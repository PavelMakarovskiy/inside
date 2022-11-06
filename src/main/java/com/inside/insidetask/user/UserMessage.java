package com.inside.insidetask.user;

import lombok.Data;

// класс для работы с телом запроса при поступлении сообщений через websocket
@Data
public class UserMessage {
    private String name;

    private String message;
}
