package com.allegro.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allegro.dto.SearchRequest;
import com.allegro.dto.SearchResultDTO;
import com.allegro.exception.SearchLimitExceededException;
import com.allegro.service.ActiveMQService;
import com.allegro.service.AllegroService;
import com.allegro.service.LiveSearchHistoryService;
import com.allegro.service.SearchLimitService;

@RestController
public class SearchController {
	
	@Autowired
	AllegroService allegroService;

	@Autowired
	SearchLimitService searchLimitService;
	
	@Autowired
	LiveSearchHistoryService liveSearchHistoryService;
	
	@Autowired
	ActiveMQService activeMQService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<SearchResultDTO> searchByTitle(@Valid @ModelAttribute SearchRequest searchRequest) throws Exception {
		String cacheKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		
		if (searchLimitService.isSearchLimitExceeded(cacheKey)) {
			throw new SearchLimitExceededException("Search Limit Exceeded");
		} else {
			searchLimitService.increaseNumberOfSearch(cacheKey);
		}
		
		liveSearchHistoryService.pushToDashboard(searchRequest);
		
		activeMQService.sendMessage(searchRequest.getSearchString());
		
		return allegroService.findOffer(searchRequest.getSearchString(), searchRequest.getResultSize(),
				searchRequest.getResultOffset());
	}
}