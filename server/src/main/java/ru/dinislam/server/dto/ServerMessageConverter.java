package ru.dinislam.server.dto;

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

@Component
@RequiredArgsConstructor
public class ServerMessageConverter implements MessageConverter {

    private final ObjectMapper objectMapper;
    private String messageId;

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException, MessageConversionException {
        final TextMessage message = session.createTextMessage(null);
        message.setJMSCorrelationID(messageId);
        if (object instanceof Response) {
            try {
                final String response = objectMapper.writeValueAsString(object);
                message.setText(response);
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
        messageId = message.getJMSCorrelationID();
        Request request = null;
        try {
            request  = objectMapper.readValue(textMessage.getText(), Request.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return request;
    }
}
