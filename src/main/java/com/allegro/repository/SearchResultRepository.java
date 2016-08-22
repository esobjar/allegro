package com.allegro.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allegro.entity.SearchResult;

@Transactional(readOnly = true)
public interface SearchResultRepository extends CrudRepository<SearchResult, Long> {

	List<SearchResult> findByTitle(String title);

	@Modifying
	@Transactional
	@Query(value = "delete from search_result sr where sr.search_history_id = :searchHistoryId", nativeQuery = true)
	void deleteSearchResults(@Param("searchHistoryId") Long searchHistoryId);
	
	@Query(value = "select * from search_result sr inner join search_history sh on sh.id = sr.search_history_id where sh.search_time between :startSearchDateTime and :endSearchDateTime", nativeQuery = true)
	List<SearchResult> findBySearchDateTimeBetween(@Param("startSearchDateTime") LocalDateTime startSearchDateTime,
			@Param("endSearchDateTime") LocalDateTime endSearchDateTime);
	
	@Query(value = "select * from search_result sr inner join search_history sh on sh.id = sr.search_history_id where sh.search_time > :searchDateTime", nativeQuery = true)
	List<SearchResult> findBySearchDateTimeAfter(@Param("searchDateTime") LocalDateTime searchDateTime);
}
