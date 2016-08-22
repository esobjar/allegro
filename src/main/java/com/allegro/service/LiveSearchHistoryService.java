package com.allegro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.allegro.dto.SearchRequest;

@Service
public class LiveSearchHistoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(LiveSearchHistoryService.class);

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Async
	public void pushToDashboard(SearchRequest searchRequest) throws InterruptedException{
		logger.info("Pushing to dashboard");
		messagingTemplate.convertAndSend("/topic/searchHistory",searchRequest);
	}
}
