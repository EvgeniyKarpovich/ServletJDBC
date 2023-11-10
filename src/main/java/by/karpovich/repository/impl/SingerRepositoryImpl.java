package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.BaseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SingerRepositoryImpl implements BaseRepository<SingerEntity, Long> {

    private static final SingerRepositoryImpl INSTANCE = new SingerRepositoryImpl();

    private SingerRepositoryImpl() {
    }

    public static SingerRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
               INSERT INTO  singers(name) 
            VALUES (?);
            """;

    private static final String DELETE_SQL = """
               DELETE FROM singers 
               WHERE id = ?;
            """;

    private static final String UPDATE_SQL = """
            UPDATE singers 
            SET name = ? 
            WHERE id = ?;
            """;

    private static final String FIND_BY_ID_SQL = """  
            SELECT id, 
            name 
            FROM singers 
            WHERE id = ?
             """;

    private static final String FIND_ALL_SQL = """
             SELECT id, 
            name 
            FROM singers;
            """;

    @Override
    public Optional<SingerEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    public Optional<SingerEntity> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            SingerEntity singerEntity = null;

            if (resultSet.next()) {
                singerEntity = buildSingerEntity(resultSet);
            }
            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public List<SingerEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SingerEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildSingerEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("IN FIND ALL");
        }
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
    public SingerEntity save(SingerEntity singerEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, singerEntity.getName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                singerEntity.setId(generatedKeys.getLong("id"));
            }
            return singerEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(SingerEntity singerEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, singerEntity.getName());
            preparedStatement.setLong(2, singerEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }

    private SingerEntity buildSingerEntity(ResultSet resultSet) throws SQLException {
        return new SingerEntity(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
