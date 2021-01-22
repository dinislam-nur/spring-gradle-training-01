package ru.dinislam.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import ru.dinislam.client.dto.Operation;
import ru.dinislam.client.dto.Request;

@SpringBootApplication
@RequiredArgsConstructor
public class ClientApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);

        final JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        final Request request = Request.builder().billId(1L).operation(Operation.CREATE).build();
        jmsTemplate.convertAndSend("request", request);
    }

}
