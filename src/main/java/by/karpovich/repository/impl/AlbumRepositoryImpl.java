package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.AlbumRepository;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.sql.AlbumSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {
    private final AlbumResultSetMapperImpl albumResultSetMapper;
    private final SingerResultSetMapperImpl singerResultSetMapper;
    private final ConnectionManagerImpl connectionManager;

    public AlbumRepositoryImpl(AlbumResultSetMapperImpl albumResultSetMapper, SingerResultSetMapperImpl singerResultSetMapper, ConnectionManagerImpl connectionManager) {
        this.albumResultSetMapper = albumResultSetMapper;
        this.singerResultSetMapper = singerResultSetMapper;
        this.connectionManager = connectionManager;
    }


    @Override
    public Optional<AlbumEntity> findById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            AlbumEntity albumEntity = null;

            if (resultSet.next()) {
                albumEntity = albumResultSetMapper.mapAlbum(resultSet);
                albumEntity.setSinger(singerResultSetMapper.mapSinger(resultSet));
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findById");
        }
    }

    public Optional<AlbumEntity> findByNameAndSingerId(String albumName, Long singerId) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_BY_NAME_AND_SINGER_ID_SQL)) {

            preparedStatement.setString(1, albumName);
            preparedStatement.setLong(2, singerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            AlbumEntity albumEntity = null;

            if (resultSet.next()) {
                albumEntity = albumResultSetMapper.mapAlbum(resultSet);
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findByNameAndSingerId");
        }
    }

    @Override
    public List<AlbumEntity> findAll() {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<AlbumEntity> result = new ArrayList<>();
            AlbumEntity albumEntity = null;

            while (resultSet.next()) {
                albumEntity = albumResultSetMapper.mapAlbum(resultSet);
                albumEntity.setSinger(singerResultSetMapper.mapSinger(resultSet));
                result.add(albumEntity);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findAll");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of deleteById");
        }
    }

    @Override
    public AlbumEntity save(AlbumEntity albumEntity) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, albumEntity.getAlbumName());
            preparedStatement.setLong(2, albumEntity.getSinger().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                albumEntity.setId(resultSet.getLong("id"));
            }

            return albumEntity;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of save");
        }
    }

    @Override
    public void update(AlbumEntity albumEntity) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.UPDATE_SQL)) {

            preparedStatement.setString(1, albumEntity.getAlbumName());
            preparedStatement.setLong(2, albumEntity.getSinger().getId());
            preparedStatement.setLong(3, albumEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of update");
        }
    }
}
