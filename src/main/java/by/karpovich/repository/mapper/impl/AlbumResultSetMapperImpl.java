package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.AlbumResultSetMapper;

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

    public AlbumEntity mapAlbumName(ResultSet resultSet) throws SQLException {
        return new AlbumEntity(
                resultSet.getString("album_name"));
    }


}
