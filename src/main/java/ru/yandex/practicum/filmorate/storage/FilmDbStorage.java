package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import javax.validation.ValidationException;
import java.util.*;

@Component
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Film addLike(int filmsId, int userId) {
        jdbcTemplate.update("INSERT INTO likes_user (film_id, user_id) VALUES(?, ?)", filmsId, userId);
        return getById(filmsId);
    }

    @Override
    public Film delLike(int filmsId, int userId) {
        jdbcTemplate.update("DELETE FROM likes_user WHERE film_id=? AND user_id=?", filmsId, userId);
        return getById(filmsId);
    }

    @Override
    public Film getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT f.id, f.film_name, f.description, f.releasedate, f.duration,  \n" +
                "m.mpa_name AS rating, m.id as mpa_id , user_id, g.genre_name AS genre, g.id as genre_id \n" +
                "FROM film f \n" +
                "LEFT JOIN mpa m ON f.mpa_id = m.id \n" +
                "LEFT JOIN film_genre fg ON f.id = fg.film_id \n" +
                "LEFT JOIN genre g ON fg.genre_id = g.id\n" +
                "LEFT JOIN likes_user lu ON lu.film_id = f.id \n" +
                "where f.id = ? order by genre_id", (rs, rowNum) -> {
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setName(rs.getString("film_name"));
            film.setDescription(rs.getString("description"));
            film.setReleaseDate(rs.getDate("releasedate").toLocalDate());
            film.setDuration(rs.getInt("duration"));
            film.setMpa(new Mpa(rs.getString("rating"), rs.getInt("mpa_id")));
            film.setLikes(new HashSet<>());
            do {
                if (rs.getString("user_id") != null) {
                    film.getLikes().add(rs.getInt("user_id"));
                }

                if (rs.getString("genre") != null) {
                    if (film.getGenres() == null) {
                        film.setGenres(new LinkedHashSet<>());
                    }
                    Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("genre"));
                    if (!film.getGenres().contains(genre)) {
                        film.getGenres().add(genre);
                    }
                }
            } while (rs.next());
            return film;
        }, id);
    }

    @Override
    public List<Film> getAll() {
        Map<Integer, Film> filmMap = new HashMap<>();
        jdbcTemplate.query("SELECT f.id, f.film_name, f.description, f.releasedate, f.duration,  \n" +
                "m.mpa_name AS rating, m.id as mpa_id , user_id, g.genre_name AS genre, g.id as genre_id \n" +
                "FROM film f \n" +
                "LEFT JOIN mpa m ON f.mpa_id = m.id \n" +
                "LEFT JOIN film_genre fg ON f.id = fg.film_id \n" +
                "LEFT JOIN genre g ON fg.genre_id = g.id\n" +
                "LEFT JOIN likes_user lu ON lu.film_id = f.id \n" +
                "ORDER BY f.id", (rs, rowNum) -> {
            int filmId = rs.getInt("id");
            Film film = filmMap.get(filmId);
            if (film == null) {
                film = new Film(
                        new HashSet<>(),
                        new HashSet<>(),
                        new Mpa(rs.getString("rating"), rs.getInt("mpa_id")),
                        filmId,
                        rs.getString("film_name"),
                        rs.getString("description"),
                        rs.getDate("releasedate").toLocalDate(),
                        rs.getInt("duration")
                );
                filmMap.put(filmId, film);
            }

            int userId = rs.getInt("user_id");
            if (userId != 0) {
                film.getLikes().add(userId);
            }

            Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("genre"));
            if (!film.getGenres().contains(genre)) {
                film.getGenres().add(genre);
            }

            return film;
        });
        return new ArrayList<>(filmMap.values());
    }


    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert insertFilm = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("film_name", film.getName());
        parameters.put("description", film.getDescription());
        parameters.put("releasedate", film.getReleaseDate());
        parameters.put("duration", film.getDuration());


        if (film.getMpa().getId() != 0) {
            if (film.getMpa().getId() > 5) throw new ValidationException("Поле mpa не может иметь такого id");
            parameters.put("mpa_id", film.getMpa().getId());
        }

        Number newId = insertFilm.executeAndReturnKey(parameters);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (genre.getId() > 6) throw new ValidationException("Поле mpa не может иметь такого id");
               jdbcTemplate.update("INSERT INTO film_genre (film_id, genre_id) VALUES(?, ?)", newId, genre.getId());
            }
        }
        return getById(newId.intValue());
    }

    @Override
    public Film updateFilm(Film film) {
        int id = film.getId();
        jdbcTemplate.update("UPDATE film SET film_name= ?, description=?, releasedate=?, duration=?, mpa_id=? WHERE id= ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), id);
        return getById(id);
    }
}
