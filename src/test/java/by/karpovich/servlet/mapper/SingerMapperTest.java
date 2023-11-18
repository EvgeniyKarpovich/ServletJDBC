package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerDtoOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SingerMapperTest {

    private static final Long ID = 1L;
    private final static String SINGER_NAME = "SingerTestName";
    private final static String ALBUM_NAME = "AlbumTestName";
    @InjectMocks
    private SingerMapper singerMapper = new SingerMapper();

    @Test
    void mapEntityFromDto() {
        SingerDto singerDto = generateSingerDto();

        SingerEntity mappedEntity = singerMapper.mapEntityFromDto(singerDto);

        assertEquals(singerDto.surname(), mappedEntity.getSurname());
    }

    @Test
    void mapFullDtoOutFromEntity() {
        SingerEntity singerEntity = generateSingerEntity();
        singerEntity.setAlbums(Arrays.asList(generateAlbumEntity()));

        SingerDtoOut mappedDto = singerMapper.mapFullDtoOutFromEntity(singerEntity);

        assertEquals(generateSingerEntity().getId(), mappedDto.id());
        assertEquals(generateSingerEntity().getSurname(), mappedDto.surname());
        assertTrue(mappedDto.albumsName().contains(generateAlbumEntity().getAlbumName()));
    }

    @Test
    void mapDtoFromEntity() {
        SingerEntity singerEntity = generateSingerEntity();

        SingerDto mappedDto = singerMapper.mapDtoFromEntity(singerEntity);

        assertEquals(singerEntity.getSurname(), mappedDto.surname());
    }

    @Test
    void mapListDtoFromListEntity() {
        SingerEntity singerEntity = generateSingerEntity();

        List<SingerEntity> singerEntities = Arrays.asList(singerEntity);

        List<SingerDto> mappedDtoList = singerMapper.mapListDtoFromListEntity(singerEntities);

        for (int i = 0; i < mappedDtoList.size(); i++) {
            assertEquals(singerEntities.get(i).getSurname(), mappedDtoList.get(i).surname());
        }
    }

    private AlbumEntity generateAlbumEntity() {
        return new AlbumEntity(ALBUM_NAME);
    }

    private SingerEntity generateSingerEntity() {
        return new SingerEntity(
                ID,
                SINGER_NAME
        );
    }

    private SingerDto generateSingerDto() {
        return new SingerDto(SINGER_NAME);
    }
}