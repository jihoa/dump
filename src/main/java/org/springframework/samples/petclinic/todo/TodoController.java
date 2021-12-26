package org.springframework.samples.petclinic.todo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Api("TodoCon")
public class TodoController {


	@ApiOperation(value = "REST APT SAMPLE", notes = "API 명세 샘플")
	@GetMapping(value = "/todoListApi")//, consumes = MediaType.APPLICATION_JSON_VALUE , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String resttest() {
		return "test11111";
	}
}
