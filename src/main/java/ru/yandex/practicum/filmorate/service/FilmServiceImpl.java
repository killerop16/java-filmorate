package ru.yandex.practicum.filmorate.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmsService {
    private static Integer COUNT_FILMS = 10;

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film getFilmById(int id) {
        checkFilmInStorage(id);
        return filmStorage.getById(id);
    }

    public Film addLike(int filmsId, int userId) {
        checkFilmAndUserInStorage(filmsId, userId);

        Film film = filmStorage.getById(filmsId);
        film.getLikes().add(userId);
        return filmStorage.addLike(filmsId, userId);
    }

    public Film delLike(int filmsId, int userId) {
        checkFilmAndUserInStorage(filmsId, userId);

        Film film = filmStorage.getById(filmsId);
        film.getLikes().remove(userId);
        return filmStorage.delLike(filmsId, userId);
    }

    public List<Film> getTopFilm(Integer count) throws Exception {
        if (count == null || count <= 0) {
            count = COUNT_FILMS;
        }

        List<Film> filmList = filmStorage.getAll();
        return filmList.stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .skip(Math.max(0, filmList.size() - count))
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Метод проверяющий наличие фильма с данным ID в хранилище
     */
    private void checkFilmInStorage(int id) {
        if (filmStorage.getById(id) == null) {
            throw new ObjectNotFoundException("Film with ID " + id + " does not exist.");
        }
    }

    /**
     * Метод проверяющий наличие фильма и пользователя с данными ID в хранилище
     */
    private void checkFilmAndUserInStorage(int id, int userId) {
        if (filmStorage.getById(id) == null) {
            throw new ObjectNotFoundException("Film with ID " + id + " does not exist.");
        }

        if (userStorage.getById(userId) == null) {
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
