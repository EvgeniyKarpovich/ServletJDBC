package by.karpovich.repository.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongResultSetMapperImpl implements SongResultSetMapper {

    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();

    @Override
    public SongEntity map(ResultSet resultSet) throws SQLException {
        SingerEntity singerEntity = new SingerEntity(
                resultSet.getLong("id"),
                resultSet.getString("surname")
        );
        AlbumEntity albumEntity = new AlbumEntity(
                resultSet.getLong("id"),
                resultSet.getString("album_name"),
                singerEntity
        );
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                singerEntity,
                albumEntity
        );
    }

    public SongEntity buildSongEntity2(ResultSet resultSet) throws SQLException {
        return new SongEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                singerRepository.findById(resultSet.getLong("singer_id"),
                        resultSet.getStatement().getConnection()).orElse(null)
        );
    }
}
