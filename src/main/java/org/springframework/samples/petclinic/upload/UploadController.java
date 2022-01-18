package org.springframework.samples.petclinic.upload;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UploadController {

	@GetMapping("/uploadForm")
	@ApiOperation("TEST")
	public String uploadForm() {
		return "upload/uploadForm";
	}
}
