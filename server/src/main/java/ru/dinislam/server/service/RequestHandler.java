package ru.dinislam.server.service;

import ru.dinislam.server.dto.Request;
import ru.dinislam.server.dto.Response;

public interface RequestHandler {

    Response processRequest(Request request);
}
