package by.karpovich.repository.mapper;

import by.karpovich.model.SingerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingerResultSetMapperImpl implements SingerResultSetMapper {

    @Override
    public SingerEntity map(ResultSet resultSet) throws SQLException {
        return new SingerEntity(
                resultSet.getLong("id"),
                resultSet.getString("surname"));
    }
}
