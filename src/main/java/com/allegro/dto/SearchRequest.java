package com.allegro.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchRequest implements Serializable{
	
	private static final long serialVersionUID = 6861628815524746248L;

	@NotEmpty
	private String searchString;
	
	@NotNull
	@Max(value=100)
	private Integer resultSize;
	
	@NotNull
	private Integer resultOffset;
	
	public SearchRequest() {
	}

	public SearchRequest(String searchString, Integer resultSize, Integer resultOffset) {
		super();
		this.searchString = searchString;
		this.resultSize = resultSize;
		this.resultOffset = resultOffset;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getResultSize() {
		return resultSize;
	}

	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}

	public Integer getResultOffset() {
		return resultOffset;
	}

	public void setResultOffset(Integer resultOffset) {
		this.resultOffset = resultOffset;
	}
	
}
