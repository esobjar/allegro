package com.allegro;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.allegro.dto.SearchHistoryDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SearchHistoryTests {

	String searchHistoryPath = "/history/";
	String firstSearchHistoryResourcePath = searchHistoryPath + "1";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getSingleSearchHistoryReturnsOK() throws Exception {
		String searchString = "koc";
		LocalDateTime dateTime = LocalDateTime.now();

		restTemplate.postForEntity(searchHistoryPath, new SearchHistoryDTO(1, searchString, dateTime),
				SearchHistoryDTO.class);

		ResponseEntity<SearchHistoryDTO> responseEntity = restTemplate.getForEntity(firstSearchHistoryResourcePath,
				SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(searchString, responseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, responseEntity.getBody().getSearchTime());
	}

	@Test
	public void getSearchHistoryNotFoundReturnsBadRequest() throws Exception {
		
		ResponseEntity<VndErrors> responseEntity = restTemplate.getForEntity(firstSearchHistoryResourcePath, VndErrors.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void postSearchHistoryReturnsCreated() throws Exception {
		String searchString = "monitor";
		LocalDateTime dateTime = LocalDateTime.now();

		ResponseEntity<SearchHistoryDTO> postResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(1, searchString, dateTime), SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.CREATED, postResponseEntity.getStatusCode());
		Assert.assertEquals(searchString, postResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, postResponseEntity.getBody().getSearchTime());

		ResponseEntity<SearchHistoryDTO> getResponseEntity = restTemplate.getForEntity(firstSearchHistoryResourcePath,
				SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
		Assert.assertEquals(searchString, getResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, getResponseEntity.getBody().getSearchTime());
	}

	@Test
	public void deleteSearchHistory() throws Exception {
		String searchString = "monitor";
		LocalDateTime dateTime = LocalDateTime.now();

		ResponseEntity<SearchHistoryDTO> postResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(1, searchString, dateTime), SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.CREATED, postResponseEntity.getStatusCode());
		Assert.assertEquals(searchString, postResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, postResponseEntity.getBody().getSearchTime());

		restTemplate.delete(firstSearchHistoryResourcePath);

		ResponseEntity<VndErrors> e = restTemplate.getForEntity(firstSearchHistoryResourcePath, VndErrors.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
	}

	@Test
	public void modifySearchHistory() throws Exception {
		String searchString = "monitor";
		String modifiedSearchString = "xbox";
		LocalDateTime dateTime = LocalDateTime.now();

		ResponseEntity<SearchHistoryDTO> postResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(1, searchString, dateTime), SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.CREATED, postResponseEntity.getStatusCode());
		Assert.assertEquals(searchString, postResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, postResponseEntity.getBody().getSearchTime());

		restTemplate.put(searchHistoryPath, new SearchHistoryDTO(1, modifiedSearchString, dateTime));

		ResponseEntity<SearchHistoryDTO> getResponseEntity = restTemplate.getForEntity(firstSearchHistoryResourcePath,
				SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
		Assert.assertEquals(modifiedSearchString, getResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, getResponseEntity.getBody().getSearchTime());
	}

	@Test
	public void getSearchHistoryListReturnsOK() throws Exception {
		String firstSearchString = "monitor";
		String secondSearchString = "xbox";
		int expectedListSize = 2;
		LocalDateTime dateTime = LocalDateTime.now();

		ResponseEntity<SearchHistoryDTO> firstPostResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(1, firstSearchString, dateTime), SearchHistoryDTO.class);
		ResponseEntity<SearchHistoryDTO> secondResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(2, secondSearchString, dateTime), SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.CREATED, firstPostResponseEntity.getStatusCode());
		Assert.assertEquals(firstSearchString, firstPostResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, firstPostResponseEntity.getBody().getSearchTime());

		Assert.assertEquals(HttpStatus.CREATED, secondResponseEntity.getStatusCode());
		Assert.assertEquals(secondSearchString, secondResponseEntity.getBody().getSearchString());
		Assert.assertEquals(dateTime, secondResponseEntity.getBody().getSearchTime());

		ParameterizedTypeReference<List<SearchHistoryDTO>> listOfSearchHistory = new ParameterizedTypeReference<List<SearchHistoryDTO>>() {
		};
		ResponseEntity<List<SearchHistoryDTO>> getResponseEntity = restTemplate.exchange(searchHistoryPath,
				HttpMethod.GET, null, listOfSearchHistory);

		Assert.assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
		Assert.assertEquals(expectedListSize, getResponseEntity.getBody().size());
	}

	@Test
	public void postSearchHistoryEmptySearchStringReturnsBadRequest() throws Exception {
		String searchString = "";
		LocalDateTime dateTime = LocalDateTime.now();

		ResponseEntity<SearchHistoryDTO> postResponseEntity = restTemplate.postForEntity(searchHistoryPath,
				new SearchHistoryDTO(1, searchString, dateTime), SearchHistoryDTO.class);

		Assert.assertEquals(HttpStatus.BAD_REQUEST, postResponseEntity.getStatusCode());
	}
}
