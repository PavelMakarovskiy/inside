package com.inside.insidetask.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inside.insidetask.user.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageHandler extends TextWebSocketHandler {
    private MessageService messageService;

    @Autowired
    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    // енд поинт для получения и отправки сообщений через websocket
    // проверка токена производится с помощью Spring Security, TokenProvider
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage messageText) throws IOException {
        UserMessage userMessage = new ObjectMapper().readValue(messageText.getPayload(), UserMessage.class);
        String message = userMessage.getMessage();
        String regex = "history (\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        int messageNumbers = 0;
        if (matcher.matches()) {
            try {
                messageNumbers = Integer.parseInt(matcher.group(1));
                List<String> messageList = messageService.getMessageList(messageNumbers);
                session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(messageList)));
            } catch (NumberFormatException exception) {
                System.out.println("NumberFormatException: " + exception.getMessage());
            }
        } else {
            messageService.addMessage(message, session);
        }
    }
}
