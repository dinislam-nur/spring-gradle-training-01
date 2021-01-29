package ru.dinislam.server.exceptions;

public class ClosedBillException extends RuntimeException {
    public ClosedBillException(String message) {
        super(message);
    }
}
