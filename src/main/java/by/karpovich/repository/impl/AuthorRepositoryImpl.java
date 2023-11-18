package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.AuthorRepository;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import by.karpovich.sql.AuthorSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static final AuthorRepositoryImpl INSTANCE = new AuthorRepositoryImpl();
    private final AuthorResultSetMapperImpl authorResultSetMapper = new AuthorResultSetMapperImpl();
    private final SongResultSetMapperImpl songResultSetMapper = new SongResultSetMapperImpl();

    private AuthorRepositoryImpl() {
    }

    public static AuthorRepositoryImpl getInstance() {
        return INSTANCE;
    }

    public Optional<AuthorEntity> findByAuthorName(String name) {
        AuthorEntity authorEntity = null;

        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorEntity = authorResultSetMapper.mapAuthor(resultSet);
            }

            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findByAuthorName");
        }
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            AuthorEntity authorEntity = null;
            List<SongEntity> songs = new ArrayList<>();

            while (resultSet.next()) {
                if (authorEntity == null) {
                    authorEntity = authorResultSetMapper.mapAuthor(resultSet);
                }

                long songId = resultSet.getLong("song_id");
                if (songId != 0) {
                    SongEntity songEntity = songResultSetMapper.mapSong(resultSet);
                    songs.add(songEntity);
                }
            }

            if (authorEntity != null) {
                authorEntity.setSongs(songs);
            }

            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findById");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<AuthorEntity> authorEntities = new ArrayList<>();

            while (resultSet.next()) {
                authorEntities.add(authorResultSetMapper.mapAuthor(resultSet));
            }

            return authorEntities;
        } catch (SQLException e) {
            throw new DaoException("IN findAll");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("IN DELETE");
        }
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, authorEntity.getAuthorName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                authorEntity.setId(resultSet.getLong("id"));
            }
            return authorEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(AuthorEntity authorEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.UPDATE_SQL)) {

            preparedStatement.setString(1, authorEntity.getAuthorName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                authorEntity.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }
}
