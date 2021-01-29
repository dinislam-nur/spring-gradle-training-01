package ru.dinislam.server.jms.excpetion_handler;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.dinislam.server.dto.Response;
import ru.dinislam.server.enums.Status;
import ru.dinislam.server.exceptions.ClosedBillException;
import ru.dinislam.server.exceptions.InsufficientFundsException;
import ru.dinislam.server.exceptions.ObjectNotFoundException;
import ru.dinislam.server.jms.Sender;

import javax.validation.ValidationException;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerImpl implements ExceptionHandler {

    private final Sender sender;

    @Override
    public void handle(Executor task) {
        try {
            task.execute();
        } catch (ObjectNotFoundException ex) {
            handleObjectNotFound();
        } catch (ClosedBillException ex) {
            handleClosedBill(ex);
        } catch (InsufficientFundsException ex) {
            handleInsufficientFunds();
        } catch (ValidationException ex) {
            handleValidation();
        } catch (DataAccessException ex) {
            handleDB();
        } catch (Throwable throwable) {
            handleThrowable();
        }
    }

    private void handleObjectNotFound() {
        final Status status = Status.BILL_NOT_EXIST;
        sender.send(Response.builder()
                        .message(status.getMessage())
                        .build(),
                status);
    }

    private void handleClosedBill(ClosedBillException closed) {
        final Status status = Status.BILL_IS_CLOSED;
        sender.send(Response.builder()
                        .message(closed.getMessage())
                        .build(),
                status);
    }

    private void handleInsufficientFunds() {
        final Status status = Status.INSUFFICIENT_FOUNDS;
        sender.send(Response.builder()
                        .message(status.getMessage())
                        .build(),
                status);
    }

    private void handleDB() {
        final Status status = Status.DB_ERROR;
        sender.send(Response.builder()
                        .message(status.getMessage())
                        .build(),
                status);
    }

    private void handleThrowable() {
        final Status status = Status.INTERNAL_ERROR;
        sender.send(Response.builder()
                        .message(status.getMessage())
                        .build(),
                status);
    }

    private void handleValidation() {
        final Status status = Status.WRONG_ARGUMENTS;
        sender.send(Response.builder()
                        .message(status.getMessage())
                        .build(),
                status);
    }
}
