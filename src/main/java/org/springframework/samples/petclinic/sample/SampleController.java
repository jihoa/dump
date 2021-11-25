package org.springframework.samples.petclinic.sample;

import org.springframework.stereotype.Controller;

//@Controller
public class SampleController {

	SampleRepository sampleRepository;

	public SampleController(SampleRepository sampleRepository){
		this.sampleRepository = sampleRepository;
	}

	public void doSomething(){
		sampleRepository.save();
	}

}
