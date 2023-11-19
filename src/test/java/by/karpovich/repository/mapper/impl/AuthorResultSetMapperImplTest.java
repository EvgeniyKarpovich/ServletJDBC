package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorResultSetMapperImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "Test Author";
    @InjectMocks
    private AuthorResultSetMapperImpl resultSetMapper;
    @Test
    void mapAuthor() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("au_id")).thenReturn(ID);
        when(resultSet.getString("au_name")).thenReturn(NAME);

        AuthorEntity result = resultSetMapper.mapAuthor(resultSet);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getAuthorName());

    }
}