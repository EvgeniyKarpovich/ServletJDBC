package by.karpovich.repository.mapper.impl;

import by.karpovich.model.SongEntity;
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
class SongResultSetMapperImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "Test Song";
    @InjectMocks
    private SongResultSetMapperImpl resultSetMapper;

    @Test
    void mapSinger() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("song_id")).thenReturn(ID);
        when(resultSet.getString("song_name")).thenReturn(NAME);

        SongEntity result = resultSetMapper.mapSong(resultSet);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getName());

    }
}