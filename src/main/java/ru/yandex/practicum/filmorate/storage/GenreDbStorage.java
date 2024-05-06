package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
@AllArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT id, genre_name FROM genre order by id", (rs, rowNum)
                -> new Genre(rs.getInt("id"), rs.getString("genre_name")));
    }

    @Override
    public Genre getById(int id) {
        return jdbcTemplate.queryForObject("SELECT genre_name FROM genre where id = ?", (rs, rowNum)
                -> new Genre(id, rs.getString("genre_name")), id);
    }
}
