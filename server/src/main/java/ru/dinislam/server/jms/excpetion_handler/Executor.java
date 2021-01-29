package ru.dinislam.server.jms.excpetion_handler;

@FunctionalInterface
public interface Executor {

    void execute() throws Throwable;
}
