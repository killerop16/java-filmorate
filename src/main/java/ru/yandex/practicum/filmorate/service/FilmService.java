package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class FilmService implements FilmsService {
    private static Integer COUNT_FILMS = 10;

    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;

    public Film getFilmById(int id) {
        checkFilmInStorage(id);
        return filmStorage.getFilms().get(id);
    }

    public Film addLike(int filmsId, int userId) {
        checkFilmAndUserInStorage(filmsId, userId);

        Film film = filmStorage.getFilms().get(filmsId);
        film.getLikes().add(userId);
        return film;
    }

    public Film delLike(int filmsId, int userId) {
        checkFilmAndUserInStorage(filmsId, userId);

        Film film = filmStorage.getFilms().get(filmsId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getTopFilm(Integer count) throws Exception {
        if (count == null || count <= 0) {
            count = COUNT_FILMS;
        }

        return filmStorage.getFilms().values().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .skip(Math.max(0, filmStorage.getFilms().size() - count))
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Метод проверяющий наличие фильма с данным ID в хранилище
     */
    private void checkFilmInStorage(int id) {
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new ObjectNotFoundException("Film with ID " + id + " does not exist.");
        }
    }

    /**
     * Метод проверяющий наличие фильма и пользователя с данными ID в хранилище
     */
    private void checkFilmAndUserInStorage(int id, int userId) {
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new ObjectNotFoundException("Film with ID " + id + " does not exist.");
        }

        if (!userStorage.getUsers().containsKey(userId)) {
            throw new ObjectNotFoundException("User with ID " + id + " does not exist.");
        }
    }

    @Override
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }
}
