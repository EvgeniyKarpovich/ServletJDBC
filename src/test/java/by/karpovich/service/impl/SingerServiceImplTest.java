package by.karpovich.service.impl;

import by.karpovich.model.SingerEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.mapper.SingerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SingerServiceImplTest {
    private static final Long ID = 1L;
    @Mock
    private SingerRepositoryImpl singerRepository;
    @Mock
    private SingerEntity singerEntityMock;
    @Mock SingerDto singerDtoMock;
    @Mock
    private SingerMapper singerMapper;
    @InjectMocks
    private SingerServiceImpl singerService;


    @Test
    void findById() {
        when(singerRepository.findById(ID)).thenReturn(Optional.of(singerEntityMock));
        when(singerMapper.mapDtoFromEntity(singerEntityMock)).thenReturn(singerDtoMock);

        SingerDto foundSinger = singerService.findById(ID);
        assertNotNull(foundSinger);
    }

    @Test
    void findByIdReturnFullDto() {
//        SingerFullDtoOut singerDto = new SingerDto();
//        SingerEntity singerEntity = new SingerEntity();
//
//        when(singerRepository.findById(anyLong())).thenReturn(Optional.of(singerEntity));
//        when(singerMapper.mapFullDtoOutFromEntity(any(SingerEntity.class))).thenReturn(singerDto);
//
//        SingerDto result = singerService.findById(14L);
//
//        assertEquals(result, singerDto);
    }

    @Test
    void findAll() {
        when(singerRepository.findAll()).thenReturn(Arrays.asList(singerEntityMock));
        when(singerMapper.mapListDtoFromListEntity(Arrays.asList(singerEntityMock))).thenReturn(Arrays.asList(singerDtoMock));

        // Act
        List<SingerDto> singers = singerService.findAll();

        // Assert
        assertEquals(1, singers.size());
    }

    @Test
    void deleteById() {
        when(singerRepository.findById(ID)).thenReturn(Optional.of(singerEntityMock));

        singerService.deleteById(1L);
    }

    //решить проблему с верифай(КОННЕКТИОН. констрейнт на уникальность)
    @Test
    void save() {
        SingerDto singerDto = new SingerDto("123124");
        SingerEntity singerEntity = new SingerEntity();

        when(singerMapper.mapEntityFromDto(any(SingerDto.class))).thenReturn(singerEntity);
        when(singerRepository.save(any(SingerEntity.class))).thenReturn(singerEntity);
        when(singerMapper.mapDtoFromEntity(any(SingerEntity.class))).thenReturn(singerDto);

        SingerDto savedSingerDto = singerService.save(singerDto);

        assertEquals(singerDto, savedSingerDto);
    }

    @Test
    void update() {
        SingerDto singerDto = new SingerDto("KKK12");

        singerService.update(singerDto, ID);
    }

    @Test
    void findSingerByIdWhichWillReturnModel() {
        when(singerEntityMock.getId()).thenReturn(ID);
        when(singerRepository.findById(ID)).thenReturn(Optional.of(singerEntityMock));

        SingerEntity foundSinger = singerService.findSingerByIdWhichWillReturnModel(ID);
        assertEquals(ID, foundSinger.getId());
    }
}