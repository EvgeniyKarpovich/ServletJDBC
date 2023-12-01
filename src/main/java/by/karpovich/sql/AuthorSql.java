package by.karpovich.sql;

public enum AuthorSql {

    SAVE_SQL("""
            INSERT INTO authors (author_name)
            VALUES (?)
            """),
    DELETE_SQL("""
            DELETE FROM authors
            WHERE id = ?
            """),

    UPDATE_SQL("""
            UPDATE authors SET author_name = ?
            WHERE id = ?
            """),

    FIND_ALL_SQL("""
            SELECT authors.id au_id,
            authors.author_name au_name
            FROM authors
            """),

    FIND_BY_ID_SQL("""
            SELECT authors.id au_id,
            authors.author_name au_name,
            songs.id song_id,
            songs.name song_name
            FROM authors
            LEFT JOIN song_author
                ON authors.id = song_author.author_id
            LEFT JOIN songs
                ON song_author.song_id = songs.id
            WHERE authors.id = ?
            """),

    FIND_BY_NAME_SQL("""
            SELECT authors.id au_id,
            authors.name au_name
            FROM authors
            WHERE authors.name = ?
            """);

    private final String sql;

    AuthorSql(String sql) { this.sql = sql; }

    public String getSql() { return sql; }
}
