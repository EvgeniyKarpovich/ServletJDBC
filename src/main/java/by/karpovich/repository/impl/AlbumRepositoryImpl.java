package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.AlbumRepository;
import by.karpovich.repository.mapper.AlbumResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {

    private static final AlbumRepositoryImpl INSTANCE = new AlbumRepositoryImpl();
    private final AlbumResultSetMapperImpl resultSetMapper = new AlbumResultSetMapperImpl();

    private AlbumRepositoryImpl() {
    }

    public static AlbumRepositoryImpl getInstance() {
        return INSTANCE;
    }

    public static final String SAVE_SQL = """
            INSERT INTO albums(album_name, singer_id)      
            VALUES (?,?)     
            """;

    private static final String DELETE_SQL = """
               DELETE FROM albums
               WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE albums
            SET album_name = ?,
            singer_id = ?,
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT
            albums.id,
            albums.album_name,
            singers.id,
            singers.surname
            FROM albums
            JOIN singers
                ON albums.singer_id = singers.id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE albums.id = ?
            """;

    @Override
    public Optional<AlbumEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            AlbumEntity albumEntity = null;

            if (resultSet.next()) {
                albumEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public List<AlbumEntity> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public AlbumEntity save(AlbumEntity albumEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, albumEntity.getAlbumName());
            preparedStatement.setLong(2, albumEntity.getSinger().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                albumEntity.setId(resultSet.getLong("id"));
            }
            return albumEntity;
        } catch (SQLException e) {
            throw new DaoException("IN REPOSITORY SAVE");
        }
    }

    @Override
    public void update(AlbumEntity albumEntity) {

    }
}
