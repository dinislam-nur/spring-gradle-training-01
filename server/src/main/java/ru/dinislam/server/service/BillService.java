package ru.dinislam.server.service;

public interface BillService {

    void fill(Long billId, Long value);

    void writeOff(Long billId, Long value);

    void transfer(Long source, Long target, Long value);

    void create(Long billId);

    void close(Long billId);

}
