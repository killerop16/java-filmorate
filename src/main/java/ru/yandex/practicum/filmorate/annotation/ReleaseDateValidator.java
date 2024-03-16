package ru.yandex.practicum.filmorate.annotation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {
    public static final LocalDate FILM_BIRTHDAY = LocalDate.of(1895, 12, 28);

    @Override
    public void initialize(ReleaseDateConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // Проверяем, что дата не раньше 28 декабря 1895 года
        return value != null && !value.isBefore(FILM_BIRTHDAY);
    }
}
