package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class MemberTest {


	private Validator createValitor() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotNameTenCharacter() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Member member = new Member();
		member.setName("spring");

		Validator validator = createValitor();

		Set<ConstraintViolation<Member>> constraintViolations = validator.validate(member);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Member> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 50");
	}

	@Test
	void shouldNotNameNotNull_ko() {
		LocaleContextHolder.setLocale(Locale.KOREA);
		Member member = new Member();
//		member.setName("spring");

		Validator validator = createValitor();

		Set<ConstraintViolation<Member>> constraintViolations = validator.validate(member);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Member> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("널이어서는 안됩니다");
	}

	@Test
	void shouldNotNameNotNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Member member = new Member();
//		member.setName("spring");

		Validator validator = createValitor();

		Set<ConstraintViolation<Member>> constraintViolations = validator.validate(member);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Member> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}



}
