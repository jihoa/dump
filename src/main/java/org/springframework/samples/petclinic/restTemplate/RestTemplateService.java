package org.springframework.samples.petclinic.restTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.sample.MemberDTO;

public interface RestTemplateService {

	String getAroundHub();

	String getName();

	String getName2();

	ResponseEntity<MemberDTO> postDto();

	ResponseEntity<MemberDTO> addHeader();

}
