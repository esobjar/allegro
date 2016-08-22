package com.allegro.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class SearchResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String title;
	@Digits(integer = 5, fraction = 2)
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "searchHistoryId", nullable = false)
	private SearchHistory searchHistory;

	protected SearchResult() {
	}

	public SearchResult(String title, BigDecimal price, SearchHistory searchHistory) {
		super();
		this.title = title;
		this.price = price;
		this.searchHistory = searchHistory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SearchHistory getSearchHistory() {
		return searchHistory;
	}

	public void setSearchHistory(SearchHistory searchHistory) {
		this.searchHistory = searchHistory;
	}

}
