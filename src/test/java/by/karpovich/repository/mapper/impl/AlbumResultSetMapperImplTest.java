package by.karpovich.repository.mapper.impl;

import by.karpovich.model.AlbumEntity;
import by.karpovich.servlet.mapper.AlbumMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.DocFlavor;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumResultSetMapperImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "Test Album";
    @InjectMocks
    private AlbumResultSetMapperImpl resultSetMapper;

    @Test
    void mapAlbum() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("al_id")).thenReturn(ID);
        when(resultSetMock.getString("al_name")).thenReturn(NAME);

        AlbumEntity result = resultSetMapper.mapAlbum(resultSetMock);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getAlbumName());
    }
}