package org.springframework.samples.petclinic.sample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutController {

	@PutMapping(value="/member1")
	public String postMemberDto1(@RequestBody MemberDTO memberDTO) {
		return memberDTO.toString();
	}

	@PutMapping(value="/member2")
	public MemberDTO postMemberDto2(@RequestBody MemberDTO memberDTO) {
		return memberDTO;
	}


	@PutMapping(value="/member3")
	public ResponseEntity<MemberDTO> postMemberDto3(@RequestBody MemberDTO memberDTO) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
	}
}
