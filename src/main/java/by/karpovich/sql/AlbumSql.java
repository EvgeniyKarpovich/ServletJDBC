package by.karpovich.sql;

public enum AlbumSql {

    SAVE_SQL("""
            INSERT INTO albums(album_name, singer_id)
            VALUES (?,?)
            """),

    DELETE_SQL("""
            DELETE FROM albums
            WHERE id = ?
            """),


    UPDATE_SQL("""
            UPDATE albums
            SET album_name = ?,
            singer_id = ?
            WHERE id = ?
            """),


    FIND_ALL_SQL("""
            SELECT albums.id al_id,
            albums.album_name al_name,
            singers.id s_id, singers.surname s_surname
            FROM albums
            JOIN singers
                ON albums.singer_id = singers.id
            """),


    FIND_BY_ID_SQL(FIND_ALL_SQL.getSql() +
            """
                    WHERE albums.id = ?
                    """),


    FIND_BY_NAME_AND_SINGER_ID_SQL("""
            SELECT albums.id al_id,
            albums.album_name al_name
            FROM albums
            WHERE album_name = ? AND singer_id = ?
            """);


    private final String sql;


    AlbumSql(String sql) {
        this.sql = sql;
    }


    public String getSql() {
        return sql;
    }
}
