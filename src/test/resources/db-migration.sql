create table if not exists singers (
 id serial PRIMARY KEY,
 surname varchar(64) not null,
 unique(surname)
);

create table if not exists albums (
 id serial PRIMARY KEY,
 album_name varchar(64) not null,
 singer_id int references singers(id) not null,
 unique(album_name, singer_id)
);

create table if not exists authors (
 id serial PRIMARY KEY,
 author_name varchar(64) not null,
 unique(author_name)
);

create table if not exists songs (
 id serial PRIMARY KEY,
 name varchar(64) not null,
 singer_id int references singers(id) not null,
 album_id int references albums(id),
 unique(name, singer_id)
);

CREATE TABLE if not exists song_author(
 song_id integer NOT NULL,
 author_id integer NOT NULL,
 PRIMARY KEY (song_id, author_id),
 CONSTRAINT fk_song FOREIGN KEY (song_id) references songs (id),
 CONSTRAINT fk_author FOREIGN KEY (author_id) references authors (id)
 );

INSERT INTO  singers(surname)
VALUES ('Singer'),
       ('Singer Delete'),
       ('Singer Update');

INSERT INTO  authors (author_name)
VALUES ('Author'),
       ('Author Delete'),
       ('Author Update');

INSERT INTO albums(album_name, singer_id)
VALUES ('Album', 1),
       ('Album Delete', 1),
       ('Album Update', 1);

INSERT INTO  songs(name, singer_id, album_id)
VALUES ('Song', 1, 1),
       ('Song Delete', 1, 1),
       ('Song Update', 1, 1);

INSERT INTO song_author (song_id, author_id)
VALUES (1, 1),
       (2, 1),
       (3, 1);
