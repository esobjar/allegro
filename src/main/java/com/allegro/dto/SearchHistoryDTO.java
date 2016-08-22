package com.allegro.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class SearchHistoryDTO {

	private Long id;
	@NotEmpty
	private String searchString;
	@DateTimeFormat
	private LocalDateTime searchTime;

	protected SearchHistoryDTO() {
	}

	public SearchHistoryDTO(long id, String searchString, LocalDateTime searchTime) {
		super();
		this.id = id;
		this.searchString = searchString;
		this.searchTime = searchTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public LocalDateTime getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(LocalDateTime searchTime) {
		this.searchTime = searchTime;
	}
}
