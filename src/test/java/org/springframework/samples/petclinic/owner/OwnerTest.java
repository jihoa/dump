package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class OwnerTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty_en() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Owner owner = new Owner();

		owner.setAge(10);

//		Person person = new Person();
//		person.setFirstName("");
//		person.setLastName("smith");

		Validator validator = createValidator();

		Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);
//		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);


//		assertThat(constraintViolations).hasSize(6);
		AssertionsForClassTypes.assertThat(constraintViolations.size()).isEqualTo(6);
		ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("age");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 20");
	}
}
