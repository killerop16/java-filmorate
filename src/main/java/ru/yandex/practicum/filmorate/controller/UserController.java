package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(generateId());
        user.setName(user.getDisplayName());
        log.info(user.toString());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID" + user.getId() + " not found");
        }
        log.info(user.toString());
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Метод генерирует уникальный Id
     */
    public  int generateId() {
        return nextId++;
    }
}
