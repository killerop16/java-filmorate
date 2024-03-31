package ru.yandex.practicum.filmorate.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {
    private final InMemoryUserStorage userStorage;

    public User getUserById(int id) {
        checkUserInStorage(id);
        return userStorage.getUsers().get(id);
    }

    public User addFriend(int id, int friendID) {
        checkUsersInStorage(id, friendID);

        User user1 = userStorage.getUsers().get(id);
        User user2 = userStorage.getUsers().get(friendID);

        user1.getFriendsId().add(user2.getId());
        user2.getFriendsId().add(user1.getId());

        return user1;
    }

    public User delFriend(int id, int friendID) {
        checkUsersInStorage(id, friendID);

        User user1 = userStorage.getUsers().get(id);
        User user2 = userStorage.getUsers().get(friendID);

        user1.getFriendsId().remove(user2.getId());
        user2.getFriendsId().remove(user1.getId());

        return user1;

    }

    public List<User> getFriends(int id) {
        checkUserInStorage(id);

        User user = userStorage.getUsers().get(id);

        List<User> friends = new ArrayList<>();
        for (Integer i : user.getFriendsId()) {
            friends.add(userStorage.getUsers().get(i));
        }
        return friends;
    }

    public List<User> getCommonFriend(int id, int otherId) {
        checkUsersInStorage(id, otherId);

        Set<Integer> set1 = userStorage.getUsers().get(id).getFriendsId();
        Set<Integer> set2 = userStorage.getUsers().get(otherId).getFriendsId();

        HashMap<Integer, User> userMap = userStorage.getUsers();

        return set1.stream()
                .filter(set2::contains)
                .map(userMap::get)
                .collect(Collectors.toList());
    }


    /**
     * Метод проверяющий наличие пользователя с данным ID в хранилище
     */
    private void checkUserInStorage(int id) {
        if (!userStorage.getUsers().containsKey(id)) {
            throw new ObjectNotFoundException("User with ID " + id + " does not exist.");
        }
    }

    /**
     * Метод проверяющий наличие пользователей с данными ID в хранилище
     */
    private void checkUsersInStorage(int id, int otherId) {
        if (!userStorage.getUsers().containsKey(id)) {
            throw new ObjectNotFoundException("User with ID " + id + " does not exist.");
        }
        if (!userStorage.getUsers().containsKey(otherId)) {
            throw new ObjectNotFoundException("User with ID " + otherId + " does not exist.");
        }
    }

    @Override
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }
}
