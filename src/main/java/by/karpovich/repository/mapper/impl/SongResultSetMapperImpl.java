package by.karpovich.repository.mapper.impl;

import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.SongResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongResultSetMapperImpl implements SongResultSetMapper {

    private AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();
    private SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();

    @Override
    public SongEntity mapSongWithAlbumAndSinger(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("song_id"),
                resultSet.getString("song_name"),
                singerResultSetMapper.mapSinger(resultSet),
                albumResultSetMapper.mapAlbum(resultSet));
    }

    @Override
    public SongEntity mapSong(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("song_id"),
                resultSet.getString("song_name")
        );
    }
}
