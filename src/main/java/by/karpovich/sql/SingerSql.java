package by.karpovich.sql;

public class SingerSql {
    public static final String SAVE_SQL = """
            INSERT INTO  singers(surname)
            VALUES (?)
            """;

    public static final String DELETE_SQL = """
            DELETE FROM singers
            WHERE id = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE singers
            SET surname = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
            singers.id s_id,
            singers.surname s_surname
            FROM singers
            """;

    public static final String FIND_BY_ID_SQL = """  
            SELECT
            singers.id s_id,
            singers.surname s_surname,
            albums.id al_id,
            albums.album_name al_name
            FROM singers
            LEFT JOIN albums
                ON albums.singer_id = singers.id
            WHERE singers.id = ?
            """;

    public static final String FIND_BY_NAME_SQL = """
            SELECT id,
            surname
            FROM singers
            WHERE surname = ?
            """;

    private SingerSql() {
    }
}
