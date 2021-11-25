package org.springframework.samples.petclinic.sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SampleControllerTest {

	@Test
	public void testDoSomething(){
		SampleRepository sampleRepository = new SampleRepository();
		SampleController sampleController = new SampleController(sampleRepository);

		sampleController.doSomething();


	}
}
