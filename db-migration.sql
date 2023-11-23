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
