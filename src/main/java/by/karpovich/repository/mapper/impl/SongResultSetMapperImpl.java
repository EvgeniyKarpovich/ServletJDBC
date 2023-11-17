package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.SongResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongResultSetMapperImpl implements SongResultSetMapper {

    @Override
    public SongEntity mapSongWithAlbumAndSinger(ResultSet resultSet) throws SQLException {
        SingerEntity singerEntity = new SingerEntity(
                resultSet.getLong("sr_id"),
                resultSet.getString("sr_surname")
        );
        AlbumEntity albumEntity = new AlbumEntity(
                resultSet.getLong("al_id"),
                resultSet.getString("al_name"),
                singerEntity
        );
        return new SongEntity(
                resultSet.getLong("song_id"),
                resultSet.getString("song_name"),
                singerEntity,
                albumEntity
        );
    }

    public SongEntity mapSong(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }

    public SongEntity mapSongName(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getString("s_name")
        );
    }
}
