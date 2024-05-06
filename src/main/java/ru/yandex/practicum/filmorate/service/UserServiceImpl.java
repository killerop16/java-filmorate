package ru.yandex.practicum.filmorate.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {
    private final UserStorage userStorage;

    public User addFriend(int id, int friendID) {
        checkUsersInStorage(id, friendID);

        User user = userStorage.getById(id);
        user.getFriendsId().add(friendID);
        return userStorage.addFriend(id, friendID);
    }

    public User delFriend(int id, int friendID) {
        checkUsersInStorage(id, friendID);

        User user = userStorage.getById(id);
        user.getFriendsId().remove(friendID);
        return userStorage.delFriend(id, friendID);
    }

    public List<User> getFriends(int id) {
        checkUserInStorage(id);
        List<User> friends = new ArrayList<>();
        User user = userStorage.getById(id);

        Set<Integer> friendIds = user.getFriendsId();
        List<Integer> friendIdsList = new ArrayList<>(friendIds);
        Collections.reverse(friendIdsList);

        for (Integer friendId : friendIdsList) {
            if (friendId == 0) break;
            friends.add(userStorage.getById(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriend(int id, int otherId) {
        checkUsersInStorage(id, otherId);
        checkUsersInStorage(id, otherId);

        Set<Integer> set1 = userStorage.getById(id).getFriendsId();
        Set<Integer> set2 = userStorage.getById(otherId).getFriendsId();

        Map<Integer, User> userMap = userStorage.getAll().stream().collect(Collectors.toMap(User::getId, Function.identity()));

        List<User> commonFriends = new ArrayList<>();

        for (Integer friendId : set1) {
            if (set2.contains(friendId)) {
                commonFriends.add(userMap.get(friendId));
            }
        }
        return commonFriends;
    }

    public User getUserById(int id) {
        checkUserInStorage(id);
        return userStorage.getById(id);
    }

    /**
     * Метод проверяющий наличие пользователя с данным ID в хранилище
     */
    private void checkUserInStorage(int id) {
        if (userStorage.getById(id) == null) {
            throw new ObjectNotFoundException("User with ID " + id + " does not exist.");
        }
    }

    /**
     * Метод проверяющий наличие пользователей с данными ID в хранилище
     */
    private void checkUsersInStorage(int id, int otherId) {
        if (userStorage.getById(id) == null) {
            throw new ObjectNotFoundException("User with ID " + id + " does not exist.");
        }
        if (userStorage.getById(otherId) == null) {
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
