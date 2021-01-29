package ru.dinislam.server.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import ru.dinislam.server.dto.Response;
import ru.dinislam.server.enums.Status;
import ru.dinislam.server.jms.Sender;

@Component
@RequiredArgsConstructor
public class SenderImpl implements Sender {

    private final JmsTemplate jmsTemplate;

    @Override
    public void send(Response response, Status status) {
        jmsTemplate.convertAndSend("response", response,
                message -> {
                    message.setIntProperty("status", status.getCode());
                    return message;
                });
    }
}
