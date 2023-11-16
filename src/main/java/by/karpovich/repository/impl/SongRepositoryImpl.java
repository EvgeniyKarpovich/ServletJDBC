package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.SongRepository;
import by.karpovich.repository.mapper.SongResultSetMapperImpl;

import java.sql.*;
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

    private static final String SAVE_SQL = """
            INSERT INTO  songs(name, singer_id, album_id)
            VALUES (?, ?, ?)
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

    private static final String FIND_BY_NAME_SQL = """
            SELECT
            id,
            name
            FROM songs
            WHERE name = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT
            songs.id song_id,
            songs.name song_name,
            singers.id sr_id,
            singers.surname sr_surname,
            albums.id al_id,
            albums.album_name al_name,
            authors.id au_id,
            authors.author_name au_name
            FROM songs 
            JOIN singers
                ON songs.singer_id = singers.id
            JOIN albums
                ON songs.album_id = albums.id
            JOIN song_author
                ON songs.id = song_author.song_id
            JOIN authors
                ON song_author.author_id = authors.id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE songs.id = ?
            """;

    private static final String INSERT_SONG_AUTHOR_SQL = """
            INSERT INTO song_author (song_id, author_id)
            VALUES (?, ?)
            """;

    private static final String FIND_BY_AUTHOR_ID_SQL = """
            SELECT
            songs.id,
            songs.name
            FROM songs
            JOIN song_author sa 
                ON songs.id = sa.song_id
            WHERE sa.author_id = ?;
            """;

    private static final String FIND_BY_AUTHOR_NAME_SQL = """
            SELECT
            songs.id,
            songs.name
            FROM songs
            JOIN song_author sa 
                ON songs.id = sa.song_id
            JOIN authors a 
                ON sa.author_id = a.id
            WHERE a.author_name = ?;
            """;


    @Override
    public Optional<SongEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
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

//    public List<SongEntity> findByAuthorId(Long id) {
//        try (var connection = ConnectionManagerImpl.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_AUTHOR_ID_SQL)) {
//            preparedStatement.setLong(1, id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<SongEntity> entities = new ArrayList<>();
//
//            while (resultSet.next()) {
//                entities.add(resultSetMapper.mapForFindByAuthorId(resultSet));
//            }
//            return entities;
//        } catch (SQLException e) {
//            throw new DaoException("IN FIND BY ID");
//        }
//    }

    public List<SongEntity> findByAuthorName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_AUTHOR_NAME_SQL)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongEntity> entities = new ArrayList<>();

            while (resultSet.next()) {
                entities.add(resultSetMapper.mapForFindByAuthorName(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY NAME");
        }
    }

    @Override
    public Optional<SongEntity> findByName(String name) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);

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

    @Override
    public List<SongEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

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
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement songAuthorStatement = connection.prepareStatement(INSERT_SONG_AUTHOR_SQL)) {

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
}
