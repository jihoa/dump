package org.springframework.samples.petclinic.todo.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class TodoTest {

    private Validator createValitor() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldNotValidateWhenNameEmpty() {
        LocaleContextHolder.setLocale(Locale.KOREA);
        Todo todo1 = new Todo();
        todo1.setUsername("");

        Validator validator = createValitor();
        Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo1);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Todo> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath()).hasToString("description");
		//@NotNull
        assertThat(violation.getMessage()).hasToString("널이어서는 안됩니다");
    }


	@Test
	void shouldNotValidateWhenNameEmpty_en() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Todo todo1 = new Todo();
		todo1.setUsername("");

		Validator validator = createValitor();
		Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo1);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Todo> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath()).hasToString("description");
		assertThat(violation.getMessage()).hasToString("must not be null");
	}

}
