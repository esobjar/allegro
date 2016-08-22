package com.allegro.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class SearchHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String searchString;
	@DateTimeFormat
	private LocalDateTime searchTime;

	protected SearchHistory() {
	}

	public SearchHistory(String searchString, LocalDateTime searchTime) {
		super();
		this.searchString = searchString;
		this.searchTime = searchTime;
	}

	public SearchHistory(Long id, String searchString, LocalDateTime searchTime) {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		SearchHistory that = (SearchHistory) obj;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}
