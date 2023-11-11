package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongRepositoryImpl implements BaseRepository<SongEntity, Long> {

    private static SongRepositoryImpl instance;
    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();

    private SongRepositoryImpl() {
    }

    public static SongRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new SongRepositoryImpl();
        }
        return instance;
    }

    private static final String CREATE_SQL = """
            INSERT INTO  songs(name, singer_id) 
            VALUES (?, ?)
            """;

    private static final String DELETE_SQL = """
               DELETE FROM songs 
               WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE songs 
            SET name = ?,
            singer_id = ?,
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT
            id,
            name
            FROM songs
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT
            id,
            name
            FROM songs
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL2 = """
            SELECT
            songs.id,
            songs.name,
            singers.id,
            singers.name
            FROM songs
            JOIN singers
                ON songs.singer_id = singers.id
            """;

    private static final String FIND_BY_ID_SQL2 = FIND_ALL_SQL2 + """
            WHERE songs.id = ?
            """;

    @Override
    public Optional<SongEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL2)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            SongEntity songEntity = null;

            if (resultSet.next()) {
                songEntity = buildSongEntity2(resultSet);
            }
            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public List<SongEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL2)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildSongEntity2(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("IN FIND ALL");
        }
    }

    private SongEntity buildSongEntity2(ResultSet resultSet) throws SQLException {
        SingerEntity singerEntity = new SingerEntity(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                singerEntity
        );
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("IN DELETE");
        }
    }

    @Override
    public SongEntity save(SongEntity songEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.setLong(2, songEntity.getSinger().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                songEntity.setId(resultSet.getLong("id"));
            }
            return songEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(SongEntity songEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.setLong(2, songEntity.getSinger().getId());
            preparedStatement.setLong(2, songEntity.getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                songEntity.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
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
}
