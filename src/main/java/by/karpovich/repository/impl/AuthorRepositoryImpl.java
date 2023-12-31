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

    private final AuthorResultSetMapperImpl authorResultSetMapper;
    private final SongResultSetMapperImpl songResultSetMapper;
    private final ConnectionManagerImpl connectionManagerImpl;

    public AuthorRepositoryImpl(AuthorResultSetMapperImpl authorResultSetMapper, SongResultSetMapperImpl songResultSetMapper, ConnectionManagerImpl connectionManagerImpl) {
        this.authorResultSetMapper = authorResultSetMapper;
        this.songResultSetMapper = songResultSetMapper;
        this.connectionManagerImpl = connectionManagerImpl;
    }

    public Optional<AuthorEntity> findByAuthorName(String name) {
        AuthorEntity authorEntity = null;

        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_BY_NAME_SQL.getSql())) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorEntity = authorResultSetMapper.mapAuthor(resultSet);
            }

            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findByAuthorName");
        }
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_BY_ID_SQL.getSql())) {
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
            throw new DaoException("Error during the execution of findById");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.FIND_ALL_SQL.getSql())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<AuthorEntity> authorEntities = new ArrayList<>();

            while (resultSet.next()) {
                authorEntities.add(authorResultSetMapper.mapAuthor(resultSet));
            }

            return authorEntities;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findAll");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.DELETE_SQL.getSql())) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of delete");
        }
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.SAVE_SQL.getSql(), Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, authorEntity.getAuthorName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                authorEntity.setId(resultSet.getLong("id"));
            }
            return authorEntity;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of save");
        }
    }

    @Override
    public void update(AuthorEntity authorEntity) {
        try (var connection = connectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AuthorSql.UPDATE_SQL.getSql())) {

            preparedStatement.setString(1, authorEntity.getAuthorName());
            preparedStatement.setLong(2, authorEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of update");
        }
    }
}
