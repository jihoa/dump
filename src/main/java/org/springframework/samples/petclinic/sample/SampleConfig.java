package org.springframework.samples.petclinic.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfig {

	@Bean
	public SampleController2 sampleController2(){
		return new SampleController2();
	}

}
