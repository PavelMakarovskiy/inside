package com.inside.insidetask.message;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
public interface MessageService {
    List<String> getMessageList(int messageNumbers);

    void addMessage(String message, WebSocketSession session);
}
