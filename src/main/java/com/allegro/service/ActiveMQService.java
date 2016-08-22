package com.allegro.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQService {
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQService.class);	

	@Autowired
    ConfigurableApplicationContext context;

    @JmsListener(destination = "search-history", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(String message) {
    	logger.info("Received <<" + message + ">>");
    }
    
    @Async
    public Message sendMessage(String msg){
    	MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        };
		
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		return jmsTemplate.sendAndReceive("search-history", messageCreator);
    }
}
