package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film getById(Integer id);

    Film addFilm(Film film);

    List<Film> getAll();

    Film updateFilm(Film film);

    Film addLike(int filmsId, int userId);

    Film delLike(int filmsId, int userId);
}
