package ru.dinislam.server.jms;

import ru.dinislam.server.dto.Request;

public interface Receiver {

    void receive(Request request);
}
