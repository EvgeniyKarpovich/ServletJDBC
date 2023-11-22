package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.impl.AlbumRepositoryImpl;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.mapper.AlbumMapper;
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

@ExtendWith(MockitoExtension.class)
class AlbumServiceImplTest {

    private static final Long ID = 1L;
    @Mock
    private AlbumRepositoryImpl albumRepository;
    @Mock
    private AlbumMapper albumMapper;
    @InjectMocks
    private AlbumServiceImpl albumService;

    @Test
    void findById() {
        AlbumEntity entity = mock(AlbumEntity.class);
        AlbumDto dto = mock(AlbumDto.class);

        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(albumMapper.mapDtoFromEntity(any(AlbumEntity.class))).thenReturn(dto);

        AlbumDto result = albumService.findById(ID);
        assertNotNull(result);
    }

    @Test
    void findAll() {
        List<AlbumEntity> entities = new ArrayList<>();
        List<AlbumDto> dtos = new ArrayList<>();

        when(albumRepository.findAll()).thenReturn(entities);
        when(albumMapper.mapListDtoFromListEntity(anyList())).thenReturn(dtos);

        List<AlbumDto> result = albumService.findAll();

        assertArrayEquals(result.toArray(), dtos.toArray());
    }

    @Test
    void deleteById() {
        AlbumEntity entity = mock(AlbumEntity.class);

        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        albumService.deleteById(ID);
    }

    @Test
    void save() {
        AlbumEntity mapped = mock(AlbumEntity.class);
        AlbumEntity saved = mock(AlbumEntity.class);

        AlbumDto startDto = mock(AlbumDto.class);
        AlbumDto returnedDto = mock(AlbumDto.class);

        when(albumMapper.mapEntityFromDto(any(AlbumDto.class))).thenReturn(mapped);
        when(albumRepository.save(any(AlbumEntity.class))).thenReturn(saved);
        when(albumMapper.mapDtoFromEntity(any(AlbumEntity.class))).thenReturn(returnedDto);

        AlbumDto result = albumService.save(startDto);

        assertEquals(result, returnedDto);
    }

    @Test
    void update() {
        AlbumEntity entity = mock(AlbumEntity.class);

        AlbumDto dto = mock(AlbumDto.class);

        when(albumMapper.mapEntityFromDto(any(AlbumDto.class))).thenReturn(entity);

        albumService.update(dto, ID);

        verify(albumMapper).mapEntityFromDto(dto);
        verify(albumRepository).update(entity);
    }

    @Test
    void findSingerByIdWhichWillReturnModel() {
        AlbumEntity AlbumEntity = mock(AlbumEntity.class);

        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(AlbumEntity));

        AlbumEntity result = albumService.findAlbumByIdWhichWillReturnModel(ID);
        assertEquals(result, AlbumEntity);
    }

    @Test
    void findByIdThrowsException() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> albumService.findById(ID));
    }

    @Test
    void saveThrowsException() {
        when(albumRepository.findByNameAndSingerId(anyString(), anyLong())).thenThrow(DuplicateException.class);

        assertThrows(DuplicateException.class, () -> albumRepository.findByNameAndSingerId("TEST NAME", 1L));
    }

    @Test
    void findAlbumByIdWhichWillReturnModelThrowsException() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class,
                () -> albumService.findAlbumByIdWhichWillReturnModel(ID));
    }
}