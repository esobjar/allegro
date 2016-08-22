package com.allegro.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allegro.dto.SearchResultDTO;
import com.allegro.service.SearchResultService;

@RestController
@RequestMapping("/result")
public class SearchResultController {

	@Autowired
	SearchResultService searchResultService;
	
	@RequestMapping(path = "/getSearchResultsBetweenSpecificDateTime", method = RequestMethod.GET)
	public ResponseEntity<List<SearchResultDTO>> getSearchHistoryBetweenSpecificDateTime(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("startSearchDateTime") LocalDateTime startSearchDateTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("endSearchDateTime") LocalDateTime endSearchDateTime){
		return new ResponseEntity<List<SearchResultDTO>>(
				searchResultService.getSearchResultsBetweenSpecificDateTime(startSearchDateTime, endSearchDateTime),
				HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getSearchResultsFromLastXDays", method = RequestMethod.GET)
	public ResponseEntity<List<SearchResultDTO>> getSearchHistoryFromLastXDays(@RequestParam("numberOfDays") Long numberOfDays){
		return new ResponseEntity<List<SearchResultDTO>>(
				searchResultService.getSearchHistoryAfterSpecificDateTime(LocalDateTime.now().minusDays(numberOfDays)),
				HttpStatus.OK);
	}
}
