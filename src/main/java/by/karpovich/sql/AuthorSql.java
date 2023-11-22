package by.karpovich.sql;

public class AuthorSql {

    public static final String SAVE_SQL = """
            INSERT INTO  authors (author_name)
            VALUES (?)
            """;

    public static final String DELETE_SQL = """
            DELETE FROM authors
            WHERE id = ?
             """;

    public static final String UPDATE_SQL = """
            UPDATE authors
            SET author_name = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
            authors.id au_id,
            authors.author_name au_name
            FROM authors
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT
            authors.id au_id,
            authors.author_name au_name,
            songs.id song_id,
            songs.name song_name
            FROM authors
            LEFT JOIN song_author
            ON authors.id = song_author.author_id
            LEFT JOIN songs
            ON song_author.song_id = songs.id
            WHERE
            authors.id  = ?
            """;

    public static final String FIND_BY_NAME_SQL = """
            SELECT
            authors.id au_id,
            authors.name au_name
            FROM authors
            WHERE authors.name = ?
            """;

    private AuthorSql() {
    }
}
