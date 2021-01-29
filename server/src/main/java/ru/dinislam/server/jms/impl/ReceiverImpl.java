package ru.dinislam.server.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.dinislam.server.dto.Request;
import ru.dinislam.server.enums.Status;
import ru.dinislam.server.jms.Receiver;
import ru.dinislam.server.jms.excpetion_handler.ExceptionHandler;
import ru.dinislam.server.service.RequestHandler;

@Component
@RequiredArgsConstructor
public class ReceiverImpl implements Receiver {

    private final RequestHandler requestHandler;
    private final ExceptionHandler exceptionHandler;
    private final SenderImpl sender;

    @JmsListener(destination = "request", containerFactory = "serverFactory")
    @Override
    public void receive(Request request) {
        exceptionHandler.handle(
                () -> sender.send(requestHandler.processRequest(request), Status.SUCCESS)
        );
    }
}
