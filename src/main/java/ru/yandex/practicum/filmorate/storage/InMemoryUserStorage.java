package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();

    private int nextId = 1;

    /**
     * Метод генерирует уникальный Id
     */
    public int generateId() {
        return nextId++;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        user.setId(generateId());
        user.setName(user.getDisplayName());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID" + user.getId() + " not found");
        }
        return users.put(user.getId(), user);
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public User addFriend(int id, int friendID) {
        return null;
    }

    @Override
    public User delFriend(int id, int friendID) {
        return null;
    }
}
