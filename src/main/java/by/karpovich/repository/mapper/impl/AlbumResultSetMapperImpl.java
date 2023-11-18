package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.AlbumResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumResultSetMapperImpl implements AlbumResultSetMapper {

    private SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();

    @Override
    public AlbumEntity mapAlbumWithSinger(ResultSet resultSet) throws SQLException {
        return new AlbumEntity(
                resultSet.getLong("al_id"),
                resultSet.getString("al_name"),
                singerResultSetMapper.mapSinger(resultSet)
                );
    }

    public AlbumEntity mapAlbum(ResultSet resultSet) throws SQLException {
        return new AlbumEntity(
                resultSet.getLong("al_id"),
                resultSet.getString("al_name"));
    }
}
