package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.SingerResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingerResultSetMapperImpl implements SingerResultSetMapper {

    @Override
    public SingerEntity mapSinger(ResultSet resultSet) throws SQLException {
        SingerEntity singerEntity = new SingerEntity();
        singerEntity.setId(resultSet.getLong("s_id"));
        singerEntity.setSurname(resultSet.getString("s_surname"));
        return singerEntity;
    }

    public AlbumEntity mapAlbumForSinger(ResultSet resultSet) throws SQLException {
        var albumEntity = new AlbumEntity();
        if (resultSet.getLong("al_id") != 0){
            albumEntity.setId(resultSet.getLong("al_id"));
            albumEntity.setAlbumName(resultSet.getString("al_name"));
        }
        return albumEntity;
    }

}
