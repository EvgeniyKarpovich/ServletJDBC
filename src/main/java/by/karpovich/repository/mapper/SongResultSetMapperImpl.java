package by.karpovich.repository.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongResultSetMapperImpl implements SongResultSetMapper {

    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();

    @Override
    public SongEntity map(ResultSet resultSet) throws SQLException {
        List<AuthorEntity> authors = new ArrayList<>();

        if (resultSet.next()) {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setId(resultSet.getLong("au_id"));
            authorEntity.setAuthorName(resultSet.getString("au_name"));
            authors.add(authorEntity);
        }
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
                albumEntity,
                authors
        );
    }

    public SongEntity mapForFindByAuthorName(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
