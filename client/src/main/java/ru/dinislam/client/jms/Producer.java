package ru.dinislam.client.jms;

import ru.dinislam.client.dto.Request;

public interface Producer {

    void send(Request request);
}
