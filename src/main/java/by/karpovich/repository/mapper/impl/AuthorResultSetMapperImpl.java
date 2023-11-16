package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.AuthorResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorResultSetMapperImpl implements AuthorResultSetMapper {
    public AuthorEntity mapAuthor(ResultSet resultSet) throws SQLException {
        return new AuthorEntity(
                resultSet.getLong("au_id"),
                resultSet.getString("au_name")
        );
    }

    public SongEntity mapSong(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getString("song_name")
        );
    }

}
