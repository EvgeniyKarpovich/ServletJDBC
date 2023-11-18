package by.karpovich.repository.mapper.impl;

import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.SingerResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingerResultSetMapperImpl implements SingerResultSetMapper {

    @Override
    public SingerEntity mapSinger(ResultSet resultSet) throws SQLException {
        return new SingerEntity(
                resultSet.getLong("s_id"),
                resultSet.getString("s_surname"));
    }
}
