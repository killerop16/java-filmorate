package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Primary
@AllArgsConstructor
public class UserDbStorage implements UserStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public User addFriend(int id, int friendID) {
        int rowsUpdated = jdbcTemplate.update("INSERT INTO useruser (userid1, userid2) VALUES(?, ?)", id, friendID);

//        if (rowsUpdated == 0) {
//            throw new ObjectNotFoundException("Object with ID does not exist in the database");
//        }

        return getById(id);
    }

    @Override
    public User delFriend(int id, int friendID) {
        int rowsUpdated = jdbcTemplate.update("DELETE FROM useruser WHERE userid1=? AND userid2=?", id, friendID);

//        if (rowsUpdated == 0) {
//            throw new ObjectNotFoundException("Object with ID does not exist in the database");
//        }

        return getById(id);
    }

    @Override
    public User getById(Integer id) {
        return jdbcTemplate.queryForObject("select id, email, login, users_name, birthday, userid1," +
                        " userid2 from users u left join useruser u2 on u.id = u2.userid1 where u.id = ?",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("users_name"));
                    user.setEmail(rs.getString("email"));
                    user.setLogin(rs.getString("login"));
                    user.setBirthday(rs.getDate("birthday").toLocalDate());

                    if (rs.getString("userid1") != null) {
                        user.setFriendsId(new HashSet<>());
                        do {
                            user.getFriendsId().add(rs.getInt("userid2"));
                        } while (rs.next());
                    }
                    return user;
                }, id);
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> userMap = new HashMap<>();

        jdbcTemplate.query("select id, email, login, users_name, birthday, userid1, userid2 " +
                "from users u left join useruser u2 on u.id = u2.userid1", (rs, rowNum) -> {
            int userId = rs.getInt("id");
            User user = userMap.get(userId);
            if (user == null) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("users_name"));
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setFriendsId(new HashSet<>());
                userMap.put(userId, user);
            }

            int userId2 = rs.getInt("userid2");

            if (userId2 != 0) {
                user.getFriendsId().add(userId2);
            }
            return user;
        });
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User addUser(User user) {
        SimpleJdbcInsert insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("login", user.getLogin());
        parameters.put("users_name", user.getName());
        parameters.put("birthday", user.getBirthday());

        Number newId = insertUser.executeAndReturnKey(parameters);
        return getById(newId.intValue());
    }

    @Override
    public User updateUser(User user) {
        int id = user.getId();

        int rowsUpdated = jdbcTemplate.update("UPDATE users SET email= ?, login= ?, users_name= ?, birthday=? WHERE id=?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), id);

        if (rowsUpdated == 0) {
            throw new ObjectNotFoundException("Object with ID does not exist in the database");
        }

        return getById(id);
    }
}
