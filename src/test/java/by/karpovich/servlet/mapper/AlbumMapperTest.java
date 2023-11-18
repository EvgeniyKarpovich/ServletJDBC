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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumMapperTest {

    private static final Long ID = 1L;
    private final static String ALBUM_NAME = "TestName";
    @Mock
    private SingerServiceImpl singerService;
    @InjectMocks
    private AlbumMapper albumMapper = new AlbumMapper();

    @Test
    void mapEntityFromDto() {
        AlbumDto albumDto = new AlbumDto(ALBUM_NAME, ID);

        SingerEntity singerEntity = new SingerEntity();
        singerEntity.setId(ID);

        when(singerService.findSingerByIdWhichWillReturnModel(ID)).thenReturn(singerEntity);

        AlbumEntity mappedEntity = albumMapper.mapEntityFromDto(albumDto);

        assertEquals(albumDto.name(), mappedEntity.getAlbumName());
        assertEquals(albumDto.singerId(), mappedEntity.getSinger().getId());
    }

    @Test
    void mapDtoFromEntity() {
        AlbumDto mappedDto = albumMapper.mapDtoFromEntity(generateAlbum());

        assertEquals(generateAlbum().getAlbumName(), mappedDto.name());
        assertEquals(generateAlbum().getSinger().getId(), mappedDto.singerId());
    }

    @Test
    void mapListDtoFromListEntity() {
        List<AlbumEntity> albumEntities = Arrays.asList(generateAlbum());
        List<AlbumDto> mappedDtoList = albumMapper.mapListDtoFromListEntity(albumEntities);

        for (int i = 0; i < mappedDtoList.size(); i++) {
            assertEquals(albumEntities.get(i).getAlbumName(), mappedDtoList.get(i).name());
            assertEquals(albumEntities.get(i).getSinger().getId(), mappedDtoList.get(i).singerId());
        }
    }

    private AlbumEntity generateAlbum() {
        return new AlbumEntity(
                ALBUM_NAME,
                generateSinger());
    }

    private SingerEntity generateSinger() {
        return new SingerEntity(
                ID);
    }
}