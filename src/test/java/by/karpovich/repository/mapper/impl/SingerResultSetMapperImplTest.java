package by.karpovich.repository.mapper.impl;

import by.karpovich.model.SingerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SingerResultSetMapperImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "Test Singer";
    @InjectMocks
    private SingerResultSetMapperImpl resultSetMapper;

    @Test
    void mapSinger() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("s_id")).thenReturn(ID);
        when(resultSet.getString("s_surname")).thenReturn(NAME);

        SingerEntity result = resultSetMapper.mapSinger(resultSet);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getSurname());

    }
}