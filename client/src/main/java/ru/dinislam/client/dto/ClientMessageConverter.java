package ru.dinislam.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientMessageConverter implements MessageConverter {

    public static final String CORRELATION_ID = UUID.randomUUID().toString();
    private final ObjectMapper objectMapper;

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException, MessageConversionException {
        final TextMessage message = session.createTextMessage(null);
        message.setJMSCorrelationID(CORRELATION_ID);
        if (object instanceof Request) {
            try {
                final String s = objectMapper.writeValueAsString(object);
                message.setText(s);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    @Override
    public Object fromMessage(Message message)
            throws JMSException, MessageConversionException {
        final TextMessage textMessage = (TextMessage) message;

        Response response = null;
        try {
            response = objectMapper.readValue(textMessage.getText(), Response.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
