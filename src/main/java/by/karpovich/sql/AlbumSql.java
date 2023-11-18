package by.karpovich.sql;

public class AlbumSql {

    public static final String SAVE_SQL = """
            INSERT INTO albums(album_name, singer_id)  
            VALUES (?,?)     
            """;

    public static final String DELETE_SQL = """
            DELETE FROM albums
            WHERE id = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE albums
            SET album_name = ?,
            singer_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
            albums.id,
            albums.album_name,
            singers.id,
            singers.surname
            FROM albums
            JOIN singers
                ON albums.singer_id = singers.id
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE albums.id = ?
            """;
    public static final String FIND_BY_NAME_AND_SINGER_ID_SQL = """
            SELECT
            id,
            album_name
            FROM albums
            WHERE name = ?
            AND singer_id = ?
            """;

    private AlbumSql() {
    }
}
