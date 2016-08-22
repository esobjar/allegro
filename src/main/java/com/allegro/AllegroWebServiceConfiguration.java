package com.allegro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.allegro.service.AllegroService;

@Configuration
public class AllegroWebServiceConfiguration {
	@Value("${allegro.apiAddress}")
	private String allegroApiAddress;

	private final String allegroMarshallerContextPath = "allegro.wsdl";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(allegroMarshallerContextPath);
		return marshaller;
	}

	@Bean
	public AllegroService allegroClient(Jaxb2Marshaller marshaller) {
		AllegroService client = new AllegroService();
		client.setDefaultUri(allegroApiAddress);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
