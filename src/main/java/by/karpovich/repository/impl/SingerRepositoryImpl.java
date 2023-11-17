package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.SingerRepository;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.sqlRequest.SingerSql;

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

    @Override
    public Optional<SingerEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SingerSql.FIND_BY_ID_SQL)) {

            stmt.setLong(1, id);
            var singerEntity = new SingerEntity();
            try (var resultSet = stmt.executeQuery()) {
                boolean singerExists = false;
                while (resultSet.next()) {
                    if (!singerExists) {
                        singerEntity = resultSetMapper.mapSinger(resultSet);
                        singerEntity.setAlbums(new ArrayList<>());
                        singerExists = true;
                    }
                    var albumEntity = resultSetMapper.mapAlbumForSinger(resultSet);
                    if (albumEntity.getId() != null) {
                        singerEntity.getAlbums().add(albumEntity);
                    }
                }
                return Optional.ofNullable(singerEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<SingerEntity> findByName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            SingerEntity singerEntity = null;

            if (resultSet.next()) {
                singerEntity = resultSetMapper.mapSinger(resultSet);
            }
            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY NAME");
        }
    }

    @Override
    public List<SingerEntity> findAll() {
        List<SingerEntity> result = new ArrayList<>();

        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSetMapper.mapSinger(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("IN FIND ALL");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("IN DELETE");
        }
    }

    @Override
    public SingerEntity save(SingerEntity singerEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

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
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.UPDATE_SQL)) {

            preparedStatement.setString(1, singerEntity.getSurname());
            preparedStatement.setLong(2, singerEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }
}
