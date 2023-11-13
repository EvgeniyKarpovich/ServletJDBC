package by.karpovich.repository.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorResultSetMapperImpl implements AuthorResultSetMapper {

    @Override
    public AuthorEntity map(ResultSet resultSet) throws SQLException {
        List<SongEntity> songs = new ArrayList<>();

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(resultSet.getLong("au_id"));
        authorEntity.setAuthorName(resultSet.getString("au_name"));

        do {
            SongEntity songEntity = new SongEntity();
            songEntity.setId(resultSet.getLong("song_id"));
            songEntity.setName(resultSet.getString("song_name"));
            songs.add(songEntity);
        } while (resultSet.next());

        authorEntity.setSongs(songs);

        return authorEntity;
    }
}
