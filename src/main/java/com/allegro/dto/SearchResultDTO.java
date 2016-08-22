package com.allegro.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;

import com.allegro.entity.SearchHistory;

public class SearchResultDTO {
	
	Long id;
	@NotEmpty
	String title;
	@Digits(integer = 5, fraction = 2)
	BigDecimal price;
	
	SearchHistory searchHistory;

	public SearchResultDTO() {

	}

	public SearchResultDTO(Long id, String title, BigDecimal price, SearchHistory searchHistory) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.searchHistory = searchHistory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SearchHistory getSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(SearchHistory searchHistory) {
		this.searchHistory = searchHistory;
	}

}
