# java-filmorate
Template repository for Filmorate project.

## Схема базы данных

![Схема базы данных](https://github.com/killerop16/java-filmorate/raw/main/photo_2024-05-01_15-36-49.jpg)

Ниже приведены примеры запросов для основных операций в нашем приложении:

1. Создание нового фильма:
   INSERT INTO film (film_name, description, releasedate, duration, mpa_id) VALUES(?, ?, ?, ?, ?);

2. Получение списка всех фильмов:
   SELECT f.id, f.film_name, f.description, f.releasedate, f.duration,  
   m.mpa_name AS rating, m.id as mpa_id , user_id, g.genre_name AS genre, g.id as genre_id
   FROM film f
   LEFT JOIN mpa m ON f.mpa_id = m.id
   LEFT JOIN film_genre fg ON f.id = fg.film_id
   LEFT JOIN genre g ON fg.genre_id = g.id
   LEFT JOIN likes_user lu ON lu.film_id = f.id
   ORDER BY f.id;

3. Получение информации о конкретном фильме:
   SELECT f.id, f.film_name, f.description, f.releasedate, f.duration,  
   m.mpa_name AS rating, m.id as mpa_id , user_id, g.genre_name AS genre, g.id as genre_id
   FROM film f
   LEFT JOIN mpa m ON f.mpa_id = m.id
   LEFT JOIN film_genre fg ON f.id = fg.film_id
   LEFT JOIN genre g ON fg.genre_id = g.id
   LEFT JOIN likes_user lu ON lu.film_id = f.id
   where f.id = ? order by genre_id;

4. Создание нового пользователя:
   INSERT INTO users (email, login, users_name, birthday) VALUES(?, ?, ?, ?);


5. Получение информации о конкретном пользователе:
   select id, email, login, users_name, birthday, userid1,userid2 from \"users\" u left join useruser u2 on u.id = u2.userid1;
   where u.id = ?;

6. Получение списка всех пользователей:
   select id, email, login, users_name, birthday, userid1, userid2 from "users" u left join useruser u2 on u.id = u2.userid1;

7. Создание дружбы между пользователями:
   INSERT INTO UserUser (userId1, userId2)
   VALUES (?,?);