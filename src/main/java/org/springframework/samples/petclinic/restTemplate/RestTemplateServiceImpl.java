package org.springframework.samples.petclinic.restTemplate;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.sample.MemberDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class RestTemplateServiceImpl implements RestTemplateService{

	private final Logger LOGGER = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
	@Override
	public String getAroundHub() {


		URI uri = UriComponentsBuilder
			.fromUriString("http://localhost:8090")
			.path("/around-hub")
			.encode()
			.build()
			.toUri();


		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);


		LOGGER.info("status code : {}", responseEntity.getStatusCode());
		LOGGER.info("body : {}", responseEntity.getBody());

		return responseEntity.getBody();
	}




	@Override
	public String getName() {

		URI uri = UriComponentsBuilder
			.fromUriString("http://localhost:8090")
			.path("/name")
			.queryParam("name", "Flature")
			.encode()
			.build()
			.toUri();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);


		LOGGER.info("status code : {}", responseEntity.getStatusCode());
		LOGGER.info("body : {}", responseEntity.getBody());

		return responseEntity.getBody();
	}

	@Override
	public String getName2() {

		URI uri = UriComponentsBuilder
			.fromUriString("http://localhost:8090")
			.path("/name2/{variable}")
			.encode()
			.build()
			.expand("Flature") // 복수 값의 경우 , 를 추가하여 구분.
			.toUri();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);


		LOGGER.info("status code : {}", responseEntity.getStatusCode());
		LOGGER.info("body : {}", responseEntity.getBody());

		return responseEntity.getBody();
	}

	@Override
	public ResponseEntity<MemberDTO> postDto() {
		URI uri = UriComponentsBuilder
			.fromUriString("http://localhost:8090")
			.path("/member")
			.queryParam("name", "Flature")
			.queryParam("mail", "11@11")
			.queryParam("organization", "IDT")
			.encode()
			.build()
			.toUri();

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setName("flature!");
		memberDTO.setEmail("aa@aa");
		memberDTO.setOrganization("ASIANA");


		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MemberDTO> responseEntity = restTemplate.postForEntity(uri, memberDTO, MemberDTO.class);


		LOGGER.info("status code : {}", responseEntity.getStatusCode());
		LOGGER.info("body : {}", responseEntity.getBody());

		return responseEntity;
	}

	@Override
	public ResponseEntity<MemberDTO> addHeader() {
		URI uri = UriComponentsBuilder
			.fromUriString("http://localhost:8090")
			.path("/add-header")
			.encode()
			.build()
			.toUri();

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setName("flature!");
		memberDTO.setEmail("aa@aa");
		memberDTO.setOrganization("ASIANA111111111");

		RequestEntity<MemberDTO> requestEntity = RequestEntity.post(uri).header("around-header", "AroundbStudio").body(memberDTO);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MemberDTO> responseEntity = restTemplate.exchange(requestEntity, MemberDTO.class);


		LOGGER.info("status code : {}", responseEntity.getStatusCode());
		LOGGER.info("body : {}", responseEntity.getBody());

		return responseEntity;
	}

}
