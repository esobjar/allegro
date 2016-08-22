package com.allegro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.allegro.dto.SearchResultDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SearchTests {

	String searchHistoryPath = "/search/";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getSearchResultsReturnsOK() throws Exception {
		final String uri = searchHistoryPath
				+ "?searchString={searchString}&resultSize={resultSize}&resultOffset={resultOffset}";
		String searchString = "koc";
		Integer resultSize = 10;
		Integer resultOffset = 0;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchString", searchString);
		params.put("resultSize", resultSize);
		params.put("resultOffset", resultOffset);

		ParameterizedTypeReference<List<SearchResultDTO>> listOfSearchResults = new ParameterizedTypeReference<List<SearchResultDTO>>() {
		};
		ResponseEntity<List<SearchResultDTO>> getResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, null,
				listOfSearchResults, params);

		Assert.assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
		Assert.assertEquals(resultSize.intValue(), getResponseEntity.getBody().size());
	}

	 @Test
	 public void getSearchResultsEmptyReturnsBadRequest() throws Exception
	 {
		 
		 final String uri = searchHistoryPath
					+ "?searchString={searchString}&resultSize={resultSize}&resultOffset={resultOffset}";
			String searchString = "";
			Integer resultSize = 10;
			Integer resultOffset = 0;

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchString", searchString);
			params.put("resultSize", resultSize);
			params.put("resultOffset", resultOffset);

			ParameterizedTypeReference<List<SearchResultDTO>> listOfSearchResults = new ParameterizedTypeReference<List<SearchResultDTO>>() {
			};
			ResponseEntity<List<SearchResultDTO>> getResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, null,
					listOfSearchResults, params);

			Assert.assertEquals(HttpStatus.BAD_REQUEST, getResponseEntity.getStatusCode());
	 }
}
