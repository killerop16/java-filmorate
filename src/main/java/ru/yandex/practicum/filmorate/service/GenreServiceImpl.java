package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private GenreStorage genreStorage;

    @Override
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    @Override
    public Genre getById(int id) {
        return genreStorage.getById(id);
    }
}
