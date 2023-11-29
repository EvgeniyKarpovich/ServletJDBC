package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.SongRepository;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import by.karpovich.sql.SongSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongRepositoryImpl implements SongRepository {
    private final SongResultSetMapperImpl songResultSetMapper;
    private final AuthorResultSetMapperImpl authorResultSetMapper;
    private final AlbumResultSetMapperImpl albumResultSetMapper;
    private final SingerResultSetMapperImpl singerResultSetMapper;

    private final ConnectionManagerImpl connectionManager;

    public SongRepositoryImpl(SongResultSetMapperImpl songResultSetMapper, AuthorResultSetMapperImpl authorResultSetMapper, AlbumResultSetMapperImpl albumResultSetMapper, SingerResultSetMapperImpl singerResultSetMapper, ConnectionManagerImpl connectionManager) {
        this.songResultSetMapper = songResultSetMapper;
        this.authorResultSetMapper = authorResultSetMapper;
        this.albumResultSetMapper = albumResultSetMapper;
        this.singerResultSetMapper = singerResultSetMapper;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<SongEntity> findById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            SongEntity songEntity = null;
            List<AuthorEntity> authors = new ArrayList<>();

            while (resultSet.next()) {
                if (songEntity == null) {
                    songEntity = songResultSetMapper.mapSong(resultSet);
                    songEntity.setAlbum(albumResultSetMapper.mapAlbum(resultSet));
                    songEntity.setSinger(singerResultSetMapper.mapSinger(resultSet));
                }
                long auId = resultSet.getLong("au_id");
                if (auId != 0) {
                    AuthorEntity authorEntity = authorResultSetMapper.mapAuthor(resultSet);
                    authors.add(authorEntity);
                }
            }
            if (songEntity != null) {
                songEntity.setAuthors(authors);
            }

            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findById");
        }
    }

    public Optional<SongEntity> findByNameAndSingerId(String songName, Long singerId) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_NAME_AND_SINGER_ID_SQL)) {
            preparedStatement.setString(1, songName);
            preparedStatement.setLong(2, singerId);

            ResultSet resultSet = preparedStatement.executeQuery();
            SongEntity songEntity = null;

            if (resultSet.next()) {
                songEntity = songResultSetMapper.mapSong(resultSet);
            }
            return Optional.ofNullable(songEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findByNameAndSingerId");
        }
    }

    public List<SongEntity> findByAuthorId(Long authorId) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_BY_AUTHOR_ID_SQL)) {
            preparedStatement.setLong(1, authorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SongEntity> songs = new ArrayList<>();
            SongEntity songEntity = null;

            while (resultSet.next()) {
                songEntity = songResultSetMapper.mapSong(resultSet);
                songs.add(songEntity);
            }

            return songs;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findByAuthorId");
        }
    }

    @Override
    public List<SongEntity> findAll() {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongEntity> result = new ArrayList<>();
            SongEntity songEntity = null;

            while (resultSet.next()) {
                songEntity = songResultSetMapper.mapSong(resultSet);
                songEntity.setAlbum(albumResultSetMapper.mapAlbum(resultSet));
                songEntity.setSinger(singerResultSetMapper.mapSinger(resultSet));
                result.add(songEntity);
            }

            return result;
        } catch (SQLException e) {
//            throw new DaoException("Error during the execution of findAll");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SongSql.DELETE_SQL);
             PreparedStatement preparedStatementDeleteSongAuthor = connection.prepareStatement(SongSql.DELETE_SONG_AUTHOR_SQL)) {

            preparedStatement.setLong(1, id);
            preparedStatementDeleteSongAuthor.setLong(1, id);
            preparedStatementDeleteSongAuthor.executeUpdate();

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of delete");
        }
    }

    @Override
    public SongEntity save(SongEntity songEntity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement songAuthorStatement = null;

        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(SongSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, songEntity.getName());
            preparedStatement.setLong(2, songEntity.getSinger().getId());
            preparedStatement.setLong(3, songEntity.getAlbum().getId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                songEntity.setId(resultSet.getLong("id"));
            }

            songAuthorStatement = connection.prepareStatement(SongSql.INSERT_SONG_AUTHOR_SQL);
            for (AuthorEntity author : songEntity.getAuthors()) {
                songAuthorStatement.setLong(1, songEntity.getId());
                songAuthorStatement.setLong(2, author.getId());
                songAuthorStatement.addBatch();
            }

            songAuthorStatement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DaoException("Error during the execution of save");
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (songAuthorStatement != null) {
                    songAuthorStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new DaoException("Error during the execution of save");
            }
        }
        return songEntity;
    }


    @Override
    public void update(SongEntity songEntity) {
        try (var connection = connectionManager.getConnection();
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
//            throw new DaoException("Error during the execution of update");
            throw new RuntimeException(e.getMessage());
        }
    }
}
