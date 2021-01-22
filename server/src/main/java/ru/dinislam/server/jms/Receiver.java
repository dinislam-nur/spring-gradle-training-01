package ru.dinislam.server.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.dinislam.server.dto.Request;

@Component
public class Receiver {

    @JmsListener(destination = "request", containerFactory = "serverFactory")
    public void receive(Request request) {
        System.out.println(request);
    }
}
