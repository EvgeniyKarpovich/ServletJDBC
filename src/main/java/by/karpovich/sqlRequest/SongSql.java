package by.karpovich.sqlRequest;

public class SongSql {
    public static final String SAVE_SQL = """
            INSERT INTO  songs(name, singer_id, album_id)
            VALUES (?, ?, ?)
            """;

    public static final String DELETE_SQL = """
               DELETE FROM songs
               WHERE id = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE songs
            SET surname = ?,
            singer_id = ?,
            album_id = ?
            WHERE id = ?
            """;

    public static final String FIND_BY_NAME_AND_SINGER_ID_SQL = """
            SELECT
            id,
            name
            FROM songs
            WHERE name = ?
            AND singer_id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
            songs.id song_id,
            songs.surname song_name,
            singers.id sr_id,
            singers.surname sr_surname,
            albums.id al_id,
            albums.album_name al_name,
            authors.id au_id,
            authors.author_name au_name
            FROM songs 
            JOIN singers
                ON songs.singer_id = singers.id
            JOIN albums
                ON songs.album_id = albums.id
            LEFT JOIN song_author
                ON songs.id = song_author.song_id
            LEFT  JOIN authors
                ON song_author.author_id = authors.id
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE songs.id = ?
            """;

    public static final String INSERT_SONG_AUTHOR_SQL = """
            INSERT INTO song_author (song_id, author_id)
            VALUES (?, ?)
            """;
    public static final String FIND_BY_AUTHOR_NAME_SQL = """
            SELECT
            songs.id,
            songs.name
            FROM songs
            JOIN song_author sa
                ON songs.id = sa.song_id
            JOIN authors a
                ON sa.author_id = a.id
            WHERE a.author_name = ?;
            """;
}
