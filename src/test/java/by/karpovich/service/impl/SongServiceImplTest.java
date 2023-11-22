package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;
import by.karpovich.servlet.mapper.SongMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {

    @Mock
    private SongMapper songMapper;
    @Mock
    private SongRepositoryImpl songRepository;
    @InjectMocks
    private SongServiceImpl songService;
    private static final Long ID = 1L;

    @Test
    void findById() {
        SongEntity entity = mock(SongEntity.class);
        SongDto dto = mock(SongDto.class);

        when(songRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(songMapper.mapSongDtoFromEntity(any(SongEntity.class))).thenReturn(dto);

        SongDto result = songService.findById(ID);
        assertNotNull(result);
    }

    @Test
    void findByIdFullDtoOut() {
        SongEntity entity = mock(SongEntity.class);
        SongDtoOut dto = mock(SongDtoOut.class);

        when(songRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(songMapper.mapSongDtoOutFromEntity(any(SongEntity.class))).thenReturn(dto);

        SongDtoOut result = songService.findByIdFullDtoOut(ID);

        assertEquals(result, dto);
    }

    @Test
    void findAll() {
        List<SongEntity> entities = new ArrayList<>();
        List<SongDto> dtos = new ArrayList<>();

        when(songRepository.findAll()).thenReturn(entities);
        when(songMapper.mapListSongDtoFromSongEntity(anyList())).thenReturn(dtos);

        List<SongDto> result = songService.findAll();

        assertArrayEquals(result.toArray(), dtos.toArray());
    }

    @Test
    void deleteById() {
        SongEntity entity = mock(SongEntity.class);

        when(songRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        songService.deleteById(ID);
    }

    @Test
    void save() {
        SongEntity mapped = mock(SongEntity.class);
        SongEntity saved = mock(SongEntity.class);

        SongDto startDto = mock(SongDto.class);
        SongDto returnedDto = mock(SongDto.class);

        when(songMapper.mapEntityFromDto(any(SongDto.class))).thenReturn(mapped);
        when(songRepository.save(any(SongEntity.class))).thenReturn(saved);
        when(songMapper.mapSongDtoFromEntity(any(SongEntity.class))).thenReturn(returnedDto);

        SongDto result = songService.save(startDto);

        assertEquals(result, returnedDto);
    }

    @Test
    void update() {
        SongEntity entity = mock(SongEntity.class);
        SongDto dto = mock(SongDto.class);

        when(songMapper.mapEntityFromDto(any(SongDto.class))).thenReturn(entity);

        songService.update(dto, ID);

        verify(songMapper).mapEntityFromDto(dto);
        verify(songRepository).update(entity);
    }

    @Test
    void findByIdThrowsException() {
        when(songRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> songService.findById(ID));
    }

    @Test
    void saveThrowsException() {
        when(songRepository.findByNameAndSingerId(anyString(), anyLong())).thenThrow(DuplicateException.class);

        assertThrows(DuplicateException.class, () -> songRepository.findByNameAndSingerId("TEST NAME", 1L));
    }
}