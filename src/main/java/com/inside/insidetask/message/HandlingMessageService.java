package com.inside.insidetask.message;

import com.inside.insidetask.user.User;
import com.inside.insidetask.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HandlingMessageService implements MessageService {
    @Value("${api.security.jwt.token.secret-key}")
    private String secretKey;

    private UserRepository userRepository;

    private MessagesRepository messagesRepository;

    @Autowired
    public HandlingMessageService(UserRepository userRepository,
                                  MessagesRepository messagesRepository) {
        this.userRepository = userRepository;
        this.messagesRepository = messagesRepository;
    }

    public List<String> getMessageList(int messageNumbers) {
        Pageable topMessages = PageRequest.of(0, messageNumbers, Sort.by("id").descending());
        messagesRepository.findAll(topMessages);
        return messagesRepository.findAll(topMessages).stream().map(messages -> messages.getText()).collect(Collectors.toList());
    }

    public void addMessage(String message, WebSocketSession session) {
        String username = session.getPrincipal().getName();
        User user = userRepository.findUserByName(username);
        Messages messages = new Messages();
        messages.setText(message);
        messages.setUser(user);
        messagesRepository.save(messages);
        user.getMessages().add(messages);
        userRepository.save(user);
    }

}
