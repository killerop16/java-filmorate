package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();

    private int nextId = 1;
    /**
     * Метод генерирует уникальный Id
     */

    public int generateId() {
        return nextId++;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film with ID" + film.getId() + " not found");
        }
        return films.put(film.getId(), film);
    }

    @Override
    public Film getById(Integer id) {
        if (films.containsKey(id)) {
           throw new ObjectNotFoundException("Film with ID " + id + " does not exist.");
        }
        return films.get(id);
    }

    @Override
    public Film addLike(int filmsId, int userId) {
        return null;
    }

    @Override
    public Film delLike(int filmsId, int userId) {
        return null;
    }
}
