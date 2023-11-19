package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.AlbumRepository;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.sql.AlbumSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {

    private static AlbumRepositoryImpl INSTANCE = new AlbumRepositoryImpl();
    private  AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();

    public AlbumRepositoryImpl() {
    }

    public static AlbumRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<AlbumEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            AlbumEntity albumEntity = null;

            if (resultSet.next()) {
                albumEntity = albumResultSetMapper.mapAlbumWithSinger(resultSet);
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    public Optional<AlbumEntity> findByNameAndSingerId(String songName, Long singerId) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_BY_NAME_AND_SINGER_ID_SQL)) {

            preparedStatement.setString(1, songName);
            preparedStatement.setLong(2, singerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            AlbumEntity albumEntity = null;

            if (resultSet.next()) {
                albumEntity = albumResultSetMapper.mapAlbum(resultSet);
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("IN findByNameAndSingerId");
        }
    }

    @Override
    public List<AlbumEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<AlbumEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(albumResultSetMapper.mapAlbumWithSinger(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("IN FIND ALL");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.DELETE_SQL)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("IN DELETE");
        }
    }

    @Override
    public AlbumEntity save(AlbumEntity albumEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
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
            throw new DaoException("IN REPOSITORY SAVE");
        }
    }

    @Override
    public void update(AlbumEntity albumEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.UPDATE_SQL)) {

            preparedStatement.setString(1, albumEntity.getAlbumName());
            preparedStatement.setLong(2, albumEntity.getSinger().getId());
            preparedStatement.setLong(3, albumEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("IN UPDATE");
        }
    }
}
