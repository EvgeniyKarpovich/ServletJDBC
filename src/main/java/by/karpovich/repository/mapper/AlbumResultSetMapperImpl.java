package by.karpovich.repository.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumResultSetMapperImpl implements AlbumResultSetMapper {

    @Override
    public AlbumEntity map(ResultSet resultSet) throws SQLException {
        SingerEntity singerEntity = new SingerEntity(
                resultSet.getLong("id"),
                resultSet.getString("surname")
        );
        return new AlbumEntity(
                resultSet.getLong("id"),
                resultSet.getString("album_name"),
                singerEntity);
    }

    public AlbumEntity map2(ResultSet resultSet) throws SQLException {
        return new AlbumEntity(
                resultSet.getLong("id"),
                resultSet.getString("album_name"));
    }
}
