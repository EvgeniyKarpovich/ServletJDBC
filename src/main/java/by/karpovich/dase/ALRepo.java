package by.karpovich.dase;

import by.karpovich.exception.DaoException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.sql.AlbumSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ALRepo {

    private final AlbumResultSetMapperImpl albumResultSetMapper;
    private final SingerResultSetMapperImpl singerResultSetMapper;
    private final ConnectionManagerImpl2 connectionManagerImpl;

    public ALRepo(AlbumResultSetMapperImpl albumResultSetMapper, SingerResultSetMapperImpl singerResultSetMapper, ConnectionManagerImpl2 connectionManagerImpl) {
        this.albumResultSetMapper = albumResultSetMapper;
        this.singerResultSetMapper = singerResultSetMapper;
        this.connectionManagerImpl = connectionManagerImpl;
    }

    public List<AlbumEntity> findAll() {
        try (var connection = connectionManagerImpl.getConnection();
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

    public AlbumEntity save(AlbumEntity albumEntity) {
        try (var connection = connectionManagerImpl.getConnection();
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
            throw new RuntimeException(e.getMessage());
        }
    }
}
