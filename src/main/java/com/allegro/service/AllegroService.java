package com.allegro.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.allegro.dto.SearchResultDTO;
import com.allegro.entity.SearchHistory;
import com.allegro.entity.SearchResult;
import com.allegro.repository.SearchHistoryRepository;
import com.allegro.repository.SearchResultRepository;

import allegro.wsdl.ArrayOfFilteroptionstype;
import allegro.wsdl.ArrayOfString;
import allegro.wsdl.DoGetItemsListRequest;
import allegro.wsdl.DoGetItemsListResponse;
import allegro.wsdl.FilterOptionsType;
import allegro.wsdl.ItemsListType;

public class AllegroService extends WebServiceGatewaySupport {

	private static final Logger logger = LoggerFactory.getLogger(AllegroService.class);

	@Value("${allegro.countryCode}")
	private int countryCode;

	@Value("${allegro.webapiKey}")
	private String webapiKey;

	@Value("${allegro.apiAddress}")
	private String allegroApiAddress;

	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Autowired
	SearchResultRepository searchResultRepository;

	public List<SearchResultDTO> findOffer(String searchString, Integer size, Integer offset) {

		DoGetItemsListRequest itemsreq = new DoGetItemsListRequest();
		itemsreq.setCountryId(countryCode);
		itemsreq.setWebapiKey(webapiKey);
		itemsreq.setResultOffset(offset);
		itemsreq.setResultSize(size);

		ArrayOfFilteroptionstype filter = new ArrayOfFilteroptionstype();
		FilterOptionsType fotSearch = new FilterOptionsType();
		fotSearch.setFilterId("search");
		ArrayOfString searchStringArray = new ArrayOfString();
		searchStringArray.getItem().add(searchString);
		fotSearch.setFilterValueId(searchStringArray);
		filter.getItem().add(fotSearch);

		itemsreq.setFilterOptions(filter);

		DoGetItemsListResponse doGetItemsList = (DoGetItemsListResponse) getWebServiceTemplate()
				.marshalSendAndReceive(allegroApiAddress, itemsreq, new SoapActionCallback("#doGetItemsList"));

		List<SearchResultDTO> items = new ArrayList<SearchResultDTO>();
		SearchHistory searchHistory = searchHistoryRepository
				.save(new SearchHistory(searchString, LocalDateTime.now()));
		
		for (ItemsListType item : doGetItemsList.getItemsList().getItem()) {
			String title = item.getItemTitle();
			BigDecimal price = new BigDecimal(Float.toString(item.getPriceInfo().getItem().get(0).getPriceValue()));
			logger.info("----------------- ");
			logger.info("Item title : " + title);
			logger.info("Price Value : " + price);

			SearchResult searchResult= searchResultRepository.save(new SearchResult(title, price, searchHistory));

			items.add(new SearchResultDTO(searchResult.getId(), searchResult.getTitle(),searchResult.getPrice(), searchResult.getSearchHistory()));
		}

		return items;
	}
}
