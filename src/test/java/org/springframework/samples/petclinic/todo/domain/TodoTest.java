package org.springframework.samples.petclinic.todo.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Disabled("validation study.........")
class TodoTest {

    private Validator createValitor() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

	@Test
	void shouldNotValidateWhenName_TenCharacter() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Todo todo1 = new Todo();
		todo1.setUsername("test");
		todo1.setDescription("10 and 50");

		Validator validator = createValitor();
		Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo1);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Todo> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("description");
		//@NotNull
		assertThat(violation.getMessage()).hasToString("size must be between 10 and 50");
	}

    @Test
    void shouldNotValidateWhenNameEmpty() {
        LocaleContextHolder.setLocale(Locale.KOREA);
        Todo todo1 = new Todo();
        todo1.setUsername("test");
		todo1.setDescription("");

        Validator validator = createValitor();
        Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo1);

//        assertThat(constraintViolations.size()).isEqualTo(0);
        ConstraintViolation<Todo> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath()).hasToString("name");
		//@NotNull
        assertThat(violation.getMessage()).hasToString("??????????????? ????????????");
    }


	@Test
	void shouldNotValidateWhenNameEmpty_en() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Todo todo1 = new Todo();
		todo1.setUsername("test");
		todo1.setDescription("");

		Validator validator = createValitor();
		Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo1);

//		assertThat(constraintViolations.size()).isEqualTo(0);
		ConstraintViolation<Todo> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("name");
		assertThat(violation.getMessage()).hasToString("must not be null");
	}

}
