package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User getById(Integer id);

    User addUser(User user);

    List<User> getAll();

    User updateUser(User user);

    User addFriend(int id, int friendID);

    User delFriend(int id, int friendID);
}
