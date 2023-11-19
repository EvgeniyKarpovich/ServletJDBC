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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SingerMapperTest {

    private static final Long ID = 1L;
    private final static String SINGER_NAME = "SingerTestName";
    private final static AlbumEntity ALBUM = new AlbumEntity(1L, "AlbumTestName");
    private final static List<AlbumEntity> ALBUMS = Arrays.asList(ALBUM);
    @InjectMocks
    private SingerMapper singerMapper = new SingerMapper();

    @Test
    void mapEntityFromDto() {
        SingerEntity result = singerMapper.mapEntityFromDto(generateSingerDto());

        assertNull(result.getId());
        assertEquals(SINGER_NAME, result.getSurname());
    }

    @Test
    void mapFullDtoOutFromEntity() {
        SingerDtoOut result = singerMapper.mapFullDtoOutFromEntity(generateSingerEntity());

        assertEquals(ID, result.id());
        assertEquals(SINGER_NAME, result.surname());
        assertEquals(ALBUMS.stream().map(AlbumEntity::getAlbumName).collect(toList()), result.albumsName());
    }

    @Test
    void mapDtoFromEntity() {
        SingerDto result = singerMapper.mapDtoFromEntity(generateSingerEntity());

        assertEquals(SINGER_NAME, result.surname());
    }

    @Test
    void mapListDtoFromListEntity() {
        SingerEntity singerEntity = generateSingerEntity();

        List<SingerEntity> singerEntities = Arrays.asList(generateSingerEntity(), generateSingerEntity());

        List<SingerDto> result = singerMapper.mapListDtoFromListEntity(singerEntities);

        assertEquals(2, result.size());

        for (SingerDto dto : result){
            assertEquals(SINGER_NAME, dto.surname());
        }
    }

    private SingerEntity generateSingerEntity() {
        return new SingerEntity(
                ID,
                SINGER_NAME,
                ALBUMS
        );
    }

    private SingerDto generateSingerDto() {
        return new SingerDto(SINGER_NAME);
    }
}