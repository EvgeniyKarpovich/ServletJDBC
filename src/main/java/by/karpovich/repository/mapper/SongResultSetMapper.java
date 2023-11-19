package by.karpovich.repository.mapper;

import by.karpovich.model.SongEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SongResultSetMapper {

    SongEntity mapSongWithAlbumAndSinger(ResultSet resultSet) throws SQLException;

    SongEntity mapSong(ResultSet resultSet) throws SQLException;
}
