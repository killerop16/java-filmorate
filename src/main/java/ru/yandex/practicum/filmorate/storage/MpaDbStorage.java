package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Component
@AllArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query("SELECT id, mpa_name FROM mpa order by id", (rs, rowNum)
                -> new Mpa(rs.getString("mpa_name"), rs.getInt("id")));
    }

    @Override
    public Mpa getById(int id) {
        return jdbcTemplate.queryForObject("SELECT mpa_name FROM mpa where id = ?", (rs, rowNum)
                -> new Mpa(rs.getString("mpa_name"), id), id);
    }
}
