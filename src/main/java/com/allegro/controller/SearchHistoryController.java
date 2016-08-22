package com.allegro.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allegro.dto.SearchHistoryDTO;
import com.allegro.exception.SearchHistoryNotFound;
import com.allegro.service.SearchHistoryService;

@RestController
@RequestMapping("/history")
public class SearchHistoryController {

	@Autowired
	SearchHistoryService searchHistoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SearchHistoryDTO>> getSearchHistory() {
		return new ResponseEntity<List<SearchHistoryDTO>>(searchHistoryService.getSearchHistory(), HttpStatus.OK);
	}

	@RequestMapping(path = "/{searchHistoryId}", method = RequestMethod.GET)
	public ResponseEntity<SearchHistoryDTO> getSearchHistory(
			@NotEmpty @PathVariable("searchHistoryId") Long searchHistoryId) throws SearchHistoryNotFound {
		return new ResponseEntity<SearchHistoryDTO>(searchHistoryService.getSearchHistory(searchHistoryId),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/{searchHistoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeSearchHistory(@PathVariable("searchHistoryId") Long searchHistoryId)
			throws SearchHistoryNotFound {
		searchHistoryService.removeSearchHistory(searchHistoryId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchHistoryDTO> addSearchHistory(@Valid @RequestBody SearchHistoryDTO searchHistoryDTO) {
		return new ResponseEntity<SearchHistoryDTO>(searchHistoryService.saveSearchHistory(searchHistoryDTO),
				HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchHistoryDTO> updateSearchHistory(@Valid @RequestBody SearchHistoryDTO searchHistoryDTO)
			throws SearchHistoryNotFound {
		return new ResponseEntity<SearchHistoryDTO>(searchHistoryService.updateSearchHistory(searchHistoryDTO),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/getSearchHistoryBetweenSpecificDateTime", method = RequestMethod.GET)
	public ResponseEntity<List<SearchHistoryDTO>> getSearchHistoryBetweenSpecificDateTime(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("startSearchDateTime") LocalDateTime startSearchDateTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("endSearchDateTime") LocalDateTime endSearchDateTime){
		return new ResponseEntity<List<SearchHistoryDTO>>(
				searchHistoryService.getSearchHistoryBetweenSpecificDateTime(startSearchDateTime, endSearchDateTime),
				HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getSearchHistoryFromLastXDays", method = RequestMethod.GET)
	public ResponseEntity<List<SearchHistoryDTO>> getSearchHistoryFromLastXDays(@RequestParam("numberOfDays") Long numberOfDays){
		return new ResponseEntity<List<SearchHistoryDTO>>(
				searchHistoryService.getSearchHistoryAfterSpecificDateTime(LocalDateTime.now().minusDays(numberOfDays)),
				HttpStatus.OK);
	}
}
