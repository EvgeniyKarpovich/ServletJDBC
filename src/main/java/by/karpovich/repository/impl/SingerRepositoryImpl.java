package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.SingerRepository;
import by.karpovich.repository.mapper.SingerResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SingerRepositoryImpl implements SingerRepository {

    private static final SingerRepositoryImpl INSTANCE = new SingerRepositoryImpl();
    private final SingerResultSetMapperImpl resultSetMapper = new SingerResultSetMapperImpl();

    private SingerRepositoryImpl() {
    }

    public static SingerRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
               INSERT INTO  singers(surname)
            VALUES (?)
            """;

    private static final String DELETE_SQL = """
               DELETE FROM singers
               WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE singers
            SET surname = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """  
            SELECT id,
            surname
            FROM singers
            WHERE id = ?
             """;

    private static final String FIND_ALL_SQL = """
             SELECT id,
            surname
            FROM singers
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT id,
            surname
            FROM singers
            WHERE surname = ?
            """;

    @Override
    public Optional<SingerEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            SingerEntity singerEntity = null;

            if (resultSet.next()) {
                singerEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public Optional<SingerEntity> findByName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            SingerEntity singerEntity = null;

            if (resultSet.next()) {
                singerEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY NAME");
        }
    }

    @Override
    public List<SingerEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SingerEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSetMapper.map(resultSet));
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

            preparedStatement.setString(1, singerEntity.getSurname());
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

            preparedStatement.setString(1, singerEntity.getSurname());
            preparedStatement.setLong(2, singerEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }
}
