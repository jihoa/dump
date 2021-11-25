package org.springframework.samples.petclinic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/around-hub")
	public String ad() {
		
		return "test11111";
		
	}
	
	@GetMapping("/name")
	public String getTest2(@RequestParam String name) {
		return name;
	}	
	
	@GetMapping("/name2/{variable}")
	public String DeleteVariable(@PathVariable String variable) {
		return variable;
	}
	
	@PostMapping("/member")
	public ResponseEntity<MemberDTO> getMember(
		@RequestBody MemberDTO memberDTO,
		@RequestParam String name,
		@RequestParam String mail,
		@RequestParam String organization
		){
		
		
		MemberDTO setmemberDTO = new MemberDTO();
		setmemberDTO.setName(name);
		setmemberDTO.setEmail(mail);
		setmemberDTO.setOrganization(organization);
		
		return ResponseEntity.status(HttpStatus.OK).body(setmemberDTO);
	}
	
	@PostMapping("/add-header")
	public ResponseEntity<MemberDTO> addHeader(
		@RequestHeader("around-header") String Header,
		@RequestBody MemberDTO memberDTO){
		
		return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
	}
	
	
}//END
	

