DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS likes_user;
DROP TABLE IF EXISTS useruser;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS mpa;

create table if not exists genre (
  id  int generated by default as identity not null primary key,
  genre_name varchar(255) not null unique
);

create table if not exists mpa (
id integer generated by default as identity not null primary key,
mpa_name varchar(200) not null unique
);

create table if not exists users (
  id  int generated by default as identity not null primary key,
  email varchar(255) NOT NULL,
  login varchar(255) NOT NULL,
  users_name varchar(255) NULL,
  birthday date
);

create table if not exists film (
  id  int generated by default as identity not null primary key,
  film_name varchar(255) NOT NULL,
  description varchar(200) NULL,
  releasedate date NULL,
  duration int NULL,
  mpa_id int NULL,
  CONSTRAINT film_mpa_id_fkey FOREIGN KEY (mpa_id) REFERENCES public.mpa(id) ON delete CASCADE
);

create table if not exists film_genre (
  film_id int NOT NULL,
  genre_id int NOT NULL,
  CONSTRAINT film_genre_pkey PRIMARY KEY (film_id, genre_id),
  CONSTRAINT film_genre_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.film(id) ON delete CASCADE,
  CONSTRAINT film_genre_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES public.genre(id) ON delete CASCADE
);

create table if not exists likes_user (
  film_id int NOT NULL,
  user_id int NOT NULL,
  CONSTRAINT likes_user_pkey PRIMARY KEY (film_id, user_id),
  CONSTRAINT likes_user_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.film(id) ON delete CASCADE,
  CONSTRAINT likes_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

create table if not exists useruser (
  userid1 int NOT NULL,
  userid2 int NOT NULL,
  CONSTRAINT useruser_pkey PRIMARY KEY (userid1, userid2),
  CONSTRAINT useruser_userid1_fkey FOREIGN KEY (userid1) REFERENCES public.users(id) ON delete CASCADE,
  CONSTRAINT useruser_userid2_fkey FOREIGN KEY (userid2) REFERENCES public.users(id) ON delete CASCADE
);