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
            authors.author_name au_name,
            songs.id song_id,
            songs.name song_name
            FROM authors
            LEFT JOIN song_author
                ON authors.id = song_author.author_id
            LEFT JOIN songs
                ON song_author.song_id = songs.id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE
            authors.id  = ?
            """;

    private static final String FIND_BY_SONG_NAME_SQL = """
            SELECT
            authors.id au_id,
            authors.author_name au_name
            FROM authors
            JOIN song_author sa
                ON authors.id = sa.author_id
            JOIN songs s
                ON sa.song_id = s.id
            WHERE s.surname = ?
            """;

    private static final String INSERT_SONG_AUTHOR_SQL = """
            INSERT INTO song_author (author_id, song_id)
            VALUES (?, ?)
            """;


//    @Override
//    public Optional<AuthorEntity> findById(Long id) {
//        AuthorEntity authorEntity = null;
//        List<SongEntity> songs = new ArrayList<>();
//        try (var connection = ConnectionManagerImpl.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
//
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                authorEntity = new AuthorEntity();
//                authorEntity.setId(resultSet.getLong("au_id"));
//                authorEntity.setAuthorName(resultSet.getString("au_name"));
//                while (resultSet.next()) {
//                    SongEntity songEntity = new SongEntity();
//                    songEntity.setId(resultSet.getLong("song_id"));
//                    songEntity.setName(resultSet.getString("song_name"));
//                    songs.add(songEntity);
//                }
//                authorEntity.setSongs(songs);
//            }
//            return Optional.ofNullable(authorEntity);
//        } catch (SQLException e) {
//            throw new DaoException("IN SAVE");
//        }
//    }

    public List<AuthorEntity> findByName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SONG_NAME_SQL)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<AuthorEntity> entities = new ArrayList<>();

            while (resultSet.next()) {
                entities.add(resultSetMapper.map2(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY NAME");
        }
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            AuthorEntity authorEntity = null;

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findById");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        return null;
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
