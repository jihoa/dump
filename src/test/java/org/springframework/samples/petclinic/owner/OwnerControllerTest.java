package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.samples.petclinic.sample.SampleController2;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class OwnerControllerTest {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void testDI(){
		SampleController2 bean = applicationContext.getBean(SampleController2.class);
		assertThat(bean).isNotNull();

	}
}
