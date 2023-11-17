package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.AlbumRepository;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.sqlRequest.AlbumSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {

    private static final AlbumRepositoryImpl INSTANCE = new AlbumRepositoryImpl();
    private final AlbumResultSetMapperImpl resultSetMapper = new AlbumResultSetMapperImpl();
    private final SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

    private AlbumRepositoryImpl() {
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
                albumEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(albumEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public List<AlbumEntity> findAll() {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AlbumSql.FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<AlbumEntity> result = new ArrayList<>();
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
