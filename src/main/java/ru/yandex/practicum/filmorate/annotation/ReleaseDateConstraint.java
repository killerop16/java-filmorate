package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
@Documented
public @interface ReleaseDateConstraint {
    String message() default "Release date must not be earlier than December 28, 1895";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
