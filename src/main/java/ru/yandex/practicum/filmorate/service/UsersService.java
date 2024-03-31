package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UsersService {
    User addUser(User user);

    List<User> getAll();

    User updateUser(User user);
}
