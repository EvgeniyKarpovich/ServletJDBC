package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumMapperTest {

    private static final Long ID = 1L;
    private static final String ALBUM_NAME = "AlbumTestName";
    private static final SingerEntity SINGER = new SingerEntity(1L, "SingerTestName");


    @Mock
    private SingerServiceImpl singerService;
    @InjectMocks
    private AlbumMapper albumMapper;

    @Test
    void mapEntityFromDto() {
        when(singerService.findSingerByIdWhichWillReturnModel(ID)).thenReturn(SINGER);

        AlbumEntity result = albumMapper.mapEntityFromDto(generateAlbumDto());

        assertNull(result.getId());
        assertEquals(ALBUM_NAME, result.getAlbumName());
        assertEquals(SINGER, result.getSinger());
    }

    @Test
    void mapDtoFromEntity() {
        AlbumDto result = albumMapper.mapDtoFromEntity(generateAlbumEntity());

        assertEquals(ALBUM_NAME, result.name());
        assertEquals(SINGER.getId(), result.singerId());
    }

    @Test
    void mapListDtoFromListEntity() {
        List<AlbumEntity> albumEntities = Arrays.asList(generateAlbumEntity(), generateAlbumEntity());

        List<AlbumDto> result = albumMapper.mapListDtoFromListEntity(albumEntities);

        for (AlbumDto dto : result) {
            assertEquals(ALBUM_NAME, dto.name());
            assertEquals(SINGER.getId(), dto.singerId());
        }
    }

    private AlbumEntity generateAlbumEntity() {
        return new AlbumEntity(
                ID,
                ALBUM_NAME,
                SINGER);
    }

    private AlbumDto generateAlbumDto() {
        return new AlbumDto(ALBUM_NAME,
                1L);
    }

}