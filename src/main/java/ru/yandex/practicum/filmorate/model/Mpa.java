package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;

@AllArgsConstructor
@Setter
@Getter
public class Mpa {

    String name;

    @Max(5)
    int id;
}
