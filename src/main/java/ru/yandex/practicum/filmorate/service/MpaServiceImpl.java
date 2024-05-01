package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    @Override
    public Mpa getById(int id) {
        return mpaStorage.getById(id);
    }
}
