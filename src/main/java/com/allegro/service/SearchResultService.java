package com.allegro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allegro.dto.SearchResultDTO;
import com.allegro.entity.SearchResult;
import com.allegro.repository.SearchResultRepository;

@Service
public class SearchResultService {

	@Autowired
	SearchResultRepository searchResultRepository;
	
	public List<SearchResultDTO> getSearchHistoryAfterSpecificDateTime(LocalDateTime searchDateTime) {

		List<SearchResultDTO> searchResults = new ArrayList<SearchResultDTO>();
		for (SearchResult sr : searchResultRepository.findBySearchDateTimeAfter(searchDateTime)) {
			searchResults.add(new SearchResultDTO(sr.getId(),sr.getTitle(), sr.getPrice(),sr.getSearchHistory()));
		}
		return searchResults;
	}
	
	public List<SearchResultDTO> getSearchResultsBetweenSpecificDateTime(LocalDateTime startSearchDateTime,
			LocalDateTime endSearchDateTime) {

		List<SearchResultDTO> searchResults = new ArrayList<SearchResultDTO>();
		for (SearchResult sr : searchResultRepository.findBySearchDateTimeBetween(startSearchDateTime, endSearchDateTime)){
			searchResults.add(new SearchResultDTO(sr.getId(), sr.getTitle(), sr.getPrice(), sr.getSearchHistory()));
		}
		return searchResults;
	}

}
