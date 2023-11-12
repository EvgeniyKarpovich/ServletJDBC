package by.karpovich.repository.mapper;

import by.karpovich.model.AuthorEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AuthorResultSetMapper {

    AuthorEntity map(ResultSet resultSet) throws SQLException;
}
