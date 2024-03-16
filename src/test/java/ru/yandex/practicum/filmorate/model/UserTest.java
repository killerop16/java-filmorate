package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getDisplayNameWhenNameIsNull() {
        User user = new User();
        user.setLogin("john_doe");

        String displayName = user.getDisplayName();

        assertEquals("john_doe", displayName);
    }

    @Test
    void getDisplayNameWhenNameIsEmpty() {
        User user = new User();
        user.setLogin("john_doe");
        user.setName("");

        String displayName = user.getDisplayName();

        assertEquals("john_doe", displayName);
    }

    @Test
    void getDisplayNameWhenNameIsNotEmpty() {
        User user = new User();
        user.setLogin("john_doe");
        user.setName("John");

        String displayName = user.getDisplayName();

        assertEquals("John", displayName);
    }
}