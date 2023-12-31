package by.karpovich.repository.mapper;

import by.karpovich.model.AlbumEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AlbumResultSetMapper {
    AlbumEntity mapAlbum(ResultSet resultSet) throws SQLException;
}
