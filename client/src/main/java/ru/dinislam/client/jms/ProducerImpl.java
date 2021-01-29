package ru.dinislam.client.jms;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.dinislam.client.dto.ClientMessageConverter;
import ru.dinislam.client.dto.Response;
import ru.dinislam.client.dto.Request;

import javax.jms.Message;

@Component
@RequiredArgsConstructor
public class ProducerImpl implements Producer {

    private final JmsTemplate jmsTemplate;
    private final ClientMessageConverter converter;

    @SneakyThrows
    @Override
    public void send(Request request) {
        jmsTemplate.convertAndSend("request", request);
        final String correlationId = ClientMessageConverter.CORRELATION_ID;
        final String filter = "JMSCorrelationID = '" + correlationId + "'";
        final Message message = jmsTemplate.receiveSelected("response", filter);
        if (message != null) {
            final int status = message.getIntProperty("status");
            System.out.println("Статус код: " + status);
            final Response response = (Response) converter.fromMessage(message);
            if (response != null) {
                System.out.println(response.getMessage());
                System.out.println(response.getPayload());
            }
        } else {
            System.out.println("Пустой ответ с корреляционным ID: " + correlationId);
        }
    }
}
