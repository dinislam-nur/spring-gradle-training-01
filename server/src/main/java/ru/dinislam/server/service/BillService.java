package ru.dinislam.server.service;

import ru.dinislam.server.dto.Response;

public interface BillService {

    Response fill(Long billId, Long value);

    Response writeOff(Long billId, Long value);

    Response transfer(Long source, Long target, Long value);

    Response create(Long billId);

    Response close(Long billId);

}
