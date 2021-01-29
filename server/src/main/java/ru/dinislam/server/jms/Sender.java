package ru.dinislam.server.jms;

import ru.dinislam.server.dto.Response;
import ru.dinislam.server.enums.Status;

public interface Sender {

    void send(Response response, Status status);

}
