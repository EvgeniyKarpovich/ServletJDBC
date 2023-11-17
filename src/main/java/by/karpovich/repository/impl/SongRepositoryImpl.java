package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.SongRepository;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import by.karpovich.sqlRequest.SongSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongRepositoryImpl implements SongRepository {
    private static final SongRepositoryImpl INSTANCE = new SongRepositoryImpl();
    private final SongResultSetMapperImpl resultSetMapper = new SongResultSetMapperImpl();

    private SongRepositoryImpl() {
    }

    public static SongRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<SongEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            SongEntity songEntity = null;

            if (resultSet.next()) {
                songEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    public List<SongEntity> findByAuthorName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_AUTHOR_NAME_SQL)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongEntity> entities = new ArrayList<>();

            while (resultSet.next()) {
                entities.add(resultSetMapper.mapForFindByAuthorName(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException("IN findByAuthorName");
        }
    }

    public Optional<SongEntity> findByNameAndSingerId(String name, Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_NAME_AND_SINGER_ID_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            SongEntity songEntity = null;

            if (resultSet.next()) {
                songEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findByName");
        }
    }

    @Override
    public List<SongEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongEntity> result = new ArrayList<>();
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
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("IN DELETE");
        }
    }

    @Override
    public SongEntity save(SongEntity songEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement songAuthorStatement = connection.prepareStatement(SongSql.INSERT_SONG_AUTHOR_SQL)) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.setLong(2, songEntity.getSinger().getId());
            preparedStatement.setLong(3, songEntity.getAlbum().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                songEntity.setId(resultSet.getLong("id"));
            }

            for (AuthorEntity author : songEntity.getAuthors()) {
                songAuthorStatement.setLong(1, songEntity.getId());
                songAuthorStatement.setLong(2, author.getId());
                songAuthorStatement.addBatch();
            }
            songAuthorStatement.executeBatch();
            connection.commit();

            return songEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(SongEntity songEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.UPDATE_SQL)) {

            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.setLong(2, songEntity.getSinger().getId());
            preparedStatement.setLong(3, songEntity.getAlbum().getId());
            preparedStatement.setLong(4, songEntity.getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                songEntity.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }
}
