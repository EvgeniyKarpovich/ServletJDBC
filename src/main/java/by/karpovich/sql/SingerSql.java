package by.karpovich.sql;

public enum SingerSql {
    SAVE_SQL("""
            INSERT INTO singers(surname)
            VALUES (?)
            """),
    DELETE_SQL("""
            DELETE
            FROM singers WHERE id = ?
            """),

    UPDATE_SQL("""
            UPDATE singers
            SET surname = ? WHERE id = ?
            """),

    FIND_ALL_SQL(""" 
            SELECT
            singers.id s_id,
            singers.surname s_surname
            FROM singers
            """),

    FIND_BY_ID_SQL("""
            SELECT
            singers.id s_id,
            singers.surname s_surname,
            albums.id al_id,
            albums.album_name al_name
            FROM singers
            LEFT JOIN albums
                ON albums.singer_id = singers.id
            WHERE singers.id = ?
            """),

    FIND_BY_NAME_SQL("""
            SELECT
            singers.id s_id,
            singers.surname s_surname
            FROM singers
            WHERE surname = ?
            """);
    private final String sql;

    SingerSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
