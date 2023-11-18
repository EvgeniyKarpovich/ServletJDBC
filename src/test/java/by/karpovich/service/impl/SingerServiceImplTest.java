package by.karpovich.service.impl;

import by.karpovich.model.SingerEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerDtoOut;
import by.karpovich.servlet.mapper.SingerMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SingerServiceImplTest {
    private static final Long ID = 1L;
    @Mock
    private SingerRepositoryImpl singerRepository;
    @Mock
    private SingerMapper singerMapper;
    @InjectMocks
    private SingerServiceImpl singerService;

    @Test
    void findById() {
        SingerEntity entity = mock(SingerEntity.class);
        SingerDto dto = mock(SingerDto.class);

        when(singerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(singerMapper.mapDtoFromEntity(any(SingerEntity.class))).thenReturn(dto);

        SingerDto foundSinger = singerService.findById(ID);
        assertNotNull(foundSinger);
    }

    @Test
    void findByIdReturnFullDto() {
        SingerEntity entity = mock(SingerEntity.class);
        SingerDtoOut dto = mock(SingerDtoOut.class);

        when(singerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(singerMapper.mapFullDtoOutFromEntity(any(SingerEntity.class))).thenReturn(dto);

        SingerDtoOut result = singerService.findByIdReturnFullDto(ID);

        assertEquals(result, dto);
    }

    @Test
    void findAll() {
        List<SingerEntity> entities = new ArrayList<>();
        List<SingerDto> dtos = new ArrayList<>();

        when(singerRepository.findAll()).thenReturn(entities);
        when(singerMapper.mapListDtoFromListEntity(anyList())).thenReturn(dtos);

        List<SingerDto> result = singerService.findAll();

        assertArrayEquals(result.toArray(), dtos.toArray());
    }

    @Test
    void deleteById() {
        SingerEntity entity = mock(SingerEntity.class);

        when(singerRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        singerService.deleteById(ID);
    }

    @Test
    void save() {
        SingerEntity validate = mock(SingerEntity.class);
        SingerEntity mapped = mock(SingerEntity.class);
        SingerEntity saved = mock(SingerEntity.class);

        SingerDto startDto = mock(SingerDto.class);
        SingerDto returnedDto = mock(SingerDto.class);

        when(singerRepository.findByName(anyString())).thenReturn(Optional.of(validate));
        when(singerMapper.mapEntityFromDto(any(SingerDto.class))).thenReturn(mapped);
        when(singerRepository.save(any(SingerEntity.class))).thenReturn(saved);
        when(singerMapper.mapDtoFromEntity(any(SingerEntity.class))).thenReturn(returnedDto);

        SingerDto result = singerService.save(startDto);

        assertEquals(result, returnedDto);
    }

    @Test
    void update() {
        SingerEntity validate = mock(SingerEntity.class);
        SingerEntity mapped = mock(SingerEntity.class);
        SingerEntity saved = mock(SingerEntity.class);

        SingerDto startDto = mock(SingerDto.class);
        SingerDto returnedDto = mock(SingerDto.class);

        when(singerRepository.findByName(anyString())).thenReturn(Optional.of(validate));
        when(singerMapper.mapEntityFromDto(any(SingerDto.class))).thenReturn(mapped);
        when(singerRepository.save(any(SingerEntity.class))).thenReturn(saved);
        when(singerMapper.mapDtoFromEntity(any(SingerEntity.class))).thenReturn(returnedDto);

        SingerDto result = singerService.save(startDto);

        assertEquals(result, returnedDto);
    }

    @Test
    void findSingerByIdWhichWillReturnModel() {
        SingerEntity singerEntity = mock(SingerEntity.class);

        when(singerRepository.findById(anyLong())).thenReturn(Optional.of(singerEntity));

        SingerEntity result = singerService.findSingerByIdWhichWillReturnModel(ID);
        assertEquals(result, singerEntity);
    }
}