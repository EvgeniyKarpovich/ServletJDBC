package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.AuthorRepository;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static final AuthorRepositoryImpl INSTANCE = new AuthorRepositoryImpl();
    private final AuthorResultSetMapperImpl resultSetMapper = new AuthorResultSetMapperImpl();

    private AuthorRepositoryImpl() {
    }

    public static AuthorRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
            INSERT INTO  authors (author_name)
            VALUES (?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM authors
            WHERE id = ?
             """;

    private static final String UPDATE_SQL = """
            UPDATE authors
            SET author_name = ?,
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT
            authors.id au_id,
            authors.author_name au_name
            FROM authors
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT
            authors.id au_id,
            authors.author_name au_name,
            songs.id song_id,
            songs.name song_name
            FROM authors
            LEFT JOIN song_author
            ON authors.id = song_author.author_id
            LEFT JOIN songs
            ON song_author.song_id = songs.id
                    WHERE
                    authors.id  = ?
                    """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT
            authors.id au_id,
            authors.name au_name
            FROM authors
            WHERE authors.name = ?
            """;

    public Optional<AuthorEntity> findByAuthorName(String name) {
        AuthorEntity authorEntity = null;

        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorEntity = resultSetMapper.mapAuthor(resultSet);
            }

            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findByAuthorName");
        }
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        AuthorEntity authorEntity = null;
        List<SongEntity> songs = new ArrayList<>();

        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (authorEntity == null) {
                    authorEntity = resultSetMapper.mapAuthor(resultSet);
                }

                Long songId = resultSet.getLong("song_id");
                if (songId != 0) {
                    SongEntity songEntity = resultSetMapper.mapSong(resultSet);
                    songs.add(songEntity);
                }
            }

            if (authorEntity != null) {
                authorEntity.setSongs(songs);
            }

        } catch (SQLException e) {
            throw new DaoException("IN findById");
        }

        return Optional.ofNullable(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        List<AuthorEntity> authorEntities = new ArrayList<>();

        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                authorEntities.add(resultSetMapper.mapAuthor(resultSet));
            }
            return authorEntities;
        } catch (SQLException e) {
            throw new DaoException("IN findAll");
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
    public AuthorEntity save(AuthorEntity authorEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

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
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

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
