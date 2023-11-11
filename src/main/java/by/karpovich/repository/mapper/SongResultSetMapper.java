package by.karpovich.repository.mapper;

import by.karpovich.model.SongEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SongResultSetMapper {

    SongEntity map(ResultSet resultSet) throws SQLException;
}
