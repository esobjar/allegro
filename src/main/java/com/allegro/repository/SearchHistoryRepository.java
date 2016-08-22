package com.allegro.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allegro.entity.SearchHistory;

@Transactional(readOnly = true)
public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Long> {

	List<SearchHistory> findBySearchString(String searchString);

	@Query(value = "select * from search_history sh where sh.search_time between :startSearchDateTime and :endSearchDateTime", nativeQuery = true)
	List<SearchHistory> findBySearchDateTimeBetween(@Param("startSearchDateTime") LocalDateTime startSearchDateTime,
			@Param("endSearchDateTime") LocalDateTime endSearchDateTime);
	
	@Query(value = "select * from search_history sh where sh.search_time > :searchDateTime", nativeQuery = true)
	List<SearchHistory> findBySearchDateTimeAfter(@Param("searchDateTime") LocalDateTime searchDateTime);
}
