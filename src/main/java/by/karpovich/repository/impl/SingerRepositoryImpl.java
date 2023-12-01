package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.SingerRepository;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.sql.SingerSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SingerRepositoryImpl implements SingerRepository {

    private final SingerResultSetMapperImpl singerResultSetMapper;
    private final AlbumResultSetMapperImpl albumResultSetMapper;
    private final ConnectionManagerImpl connectionManager;

    public SingerRepositoryImpl(SingerResultSetMapperImpl singerResultSetMapper, AlbumResultSetMapperImpl albumResultSetMapper, ConnectionManagerImpl connectionManager) {
        this.singerResultSetMapper = singerResultSetMapper;
        this.albumResultSetMapper = albumResultSetMapper;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<SingerEntity> findById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.FIND_BY_ID_SQL.getSql())) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            SingerEntity singerEntity = null;
            List<AlbumEntity> albums = new ArrayList<>();

            while (resultSet.next()) {
                if (singerEntity == null) {
                    singerEntity = singerResultSetMapper.mapSinger(resultSet);
                }

                long albumId = resultSet.getLong("al_id");
                if (albumId != 0) {
                    AlbumEntity albumEntity = albumResultSetMapper.mapAlbum(resultSet);
                    albums.add(albumEntity);
                }
            }
            if (singerEntity != null) {
                singerEntity.setAlbums(albums);
            }

            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findById");
        }
    }

    @Override
    public Optional<SingerEntity> findByName(String name) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.FIND_BY_NAME_SQL.getSql())) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            SingerEntity singerEntity = null;

            if (resultSet.next()) {
                singerEntity = singerResultSetMapper.mapSinger(resultSet);
            }
            return Optional.ofNullable(singerEntity);
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findByName");
        }
    }

    @Override
    public List<SingerEntity> findAll() {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.FIND_ALL_SQL.getSql())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SingerEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(singerResultSetMapper.mapSinger(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of findAll");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.DELETE_SQL.getSql())) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of delete");
        }
    }

    @Override
    public SingerEntity save(SingerEntity singerEntity) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.SAVE_SQL.getSql(), Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, singerEntity.getSurname());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                singerEntity.setId(generatedKeys.getLong("id"));
            }
            return singerEntity;
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of save");
        }
    }

    @Override
    public void update(SingerEntity singerEntity) {
        try (var connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SingerSql.UPDATE_SQL.getSql())) {

            preparedStatement.setString(1, singerEntity.getSurname());
            preparedStatement.setLong(2, singerEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during the execution of update");
        }
    }
}
