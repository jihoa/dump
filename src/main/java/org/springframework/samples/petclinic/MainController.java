package org.springframework.samples.petclinic;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String main() {
		return "home";
	}


//	@GetMapping("/")
//	public String main() {
//		return "welcome";
//	}
}
