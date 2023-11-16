package by.karpovich.repository.mapper;

import by.karpovich.model.SingerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SingerResultSetMapper {

    SingerEntity mapSinger(ResultSet resultSet) throws SQLException;
}
