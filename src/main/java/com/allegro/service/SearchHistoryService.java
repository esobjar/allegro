package com.allegro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allegro.dto.SearchHistoryDTO;
import com.allegro.entity.SearchHistory;
import com.allegro.exception.SearchHistoryNotFound;
import com.allegro.repository.SearchHistoryRepository;
import com.allegro.repository.SearchResultRepository;

@Service
public class SearchHistoryService {
	private static final Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);

	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Autowired
	SearchResultRepository searchResultRepository;

	public List<SearchHistoryDTO> getSearchHistory() {

		List<SearchHistoryDTO> searchHistory = new ArrayList<SearchHistoryDTO>();
		for (SearchHistory sh : searchHistoryRepository.findAll()) {
			searchHistory.add(new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime()));
		}
		return searchHistory;
	}

	public SearchHistoryDTO getSearchHistory(Long searchHistoryId) throws SearchHistoryNotFound {
		SearchHistory sh = findSearchHistory(searchHistoryId);
		return new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime());
	}

	@Transactional
	public void removeSearchHistory(Long searchHistoryId) throws SearchHistoryNotFound {
		findSearchHistory(searchHistoryId);
		searchResultRepository.deleteSearchResults(searchHistoryId);
		searchHistoryRepository.delete(searchHistoryId);
	}

	@Transactional
	public SearchHistoryDTO saveSearchHistory(SearchHistoryDTO searchHistoryDTO) {
		SearchHistory sh = searchHistoryRepository
				.save(new SearchHistory(searchHistoryDTO.getSearchString(), searchHistoryDTO.getSearchTime()));
		return new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime());
	}

	@Transactional
	public SearchHistoryDTO updateSearchHistory(SearchHistoryDTO searchHistoryDTO) throws SearchHistoryNotFound {
		SearchHistory sh = findSearchHistory(searchHistoryDTO.getId());
		sh.setSearchString(searchHistoryDTO.getSearchString());
		sh.setSearchTime(searchHistoryDTO.getSearchTime());
		sh = searchHistoryRepository.save(sh);
		return new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime());
	}

	private SearchHistory findSearchHistory(Long id) throws SearchHistoryNotFound {
		SearchHistory sh = searchHistoryRepository.findOne(id);
		if (sh == null) {
			logger.info("Search History not found. id: " + id);
			throw new SearchHistoryNotFound("Search History not found for id: " + id);
		}
		return sh;
	}

	public List<SearchHistoryDTO> getSearchHistoryBetweenSpecificDateTime(LocalDateTime startSearchDateTime,
			LocalDateTime endSearchDateTime) {

		List<SearchHistoryDTO> searchHistory = new ArrayList<SearchHistoryDTO>();
		for (SearchHistory sh : searchHistoryRepository.findBySearchDateTimeBetween(startSearchDateTime, endSearchDateTime)) {
			searchHistory.add(new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime()));
		}
		return searchHistory;
	}
	
	public List<SearchHistoryDTO> getSearchHistoryAfterSpecificDateTime(LocalDateTime searchDateTime) {

		List<SearchHistoryDTO> searchHistory = new ArrayList<SearchHistoryDTO>();
		for (SearchHistory sh : searchHistoryRepository.findBySearchDateTimeAfter(searchDateTime)) {
			searchHistory.add(new SearchHistoryDTO(sh.getId(), sh.getSearchString(), sh.getSearchTime()));
		}
		return searchHistory;
	}

}
