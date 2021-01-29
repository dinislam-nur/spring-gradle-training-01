package ru.dinislam.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory<?> serverFactory(
            @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer
    ) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public BrokerService broker() throws Exception {
        final BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.setPersistent(false);
        return brokerService;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
