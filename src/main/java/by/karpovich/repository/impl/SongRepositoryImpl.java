package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class SongRepositoryImpl implements BaseRepository<SongEntity, Long> {

    private static final SongRepositoryImpl INSTANCE = new SongRepositoryImpl();
    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();

    private SongRepositoryImpl() {
    }

    public static SongRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private static final String FIND_BY_ID_SQL = """
            SELECT 
            id,
            name
            FROM songs
            WHERE id = ?; 
            """;

    private static final String CREATE_SQL = """
            INSERT INTO  songs(name, singer_id) 
            VALUES (?, ?)
            """;

    @Override
    public Optional<SongEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            SongEntity songEntity = null;
            if (resultSet.next()) {
                songEntity = buildSongEntity(resultSet);
            }
            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    private SongEntity buildSongEntity(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                singerRepository.findById(resultSet.getLong("singer_id"),
                        resultSet.getStatement().getConnection()).orElse(null)
        );
    }

    @Override
    public List<SongEntity> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public SongEntity save(SongEntity songEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                songEntity.setId(generatedKeys.getLong("id"));
            }
            return songEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(SongEntity songEntity) {
    }
}
