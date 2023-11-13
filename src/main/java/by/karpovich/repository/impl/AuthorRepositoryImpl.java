package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.AuthorRepository;
import by.karpovich.repository.mapper.AuthorResultSetMapperImpl;

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
                    
            """;

    private static final String UPDATE_SQL = """
                     
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

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        AuthorEntity authorEntity = null;
        List<SongEntity> songs = new ArrayList<>();
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
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

    }
}
