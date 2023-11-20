package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongMapperTest {

    private static final Long ID = 1L;
    private static final String SONG_NAME = "SongTestName";
    private static final SingerEntity SINGER = new SingerEntity(1L, "SingerTestName");
    private static final AlbumEntity ALBUM = new AlbumEntity(1L, "AlbumTestName");
    private static final AuthorEntity AUTHOR = new AuthorEntity(1L, "AuthorName");
    private static final List<AuthorEntity> AUTHORS = Arrays.asList(AUTHOR);
    @Mock
    private SingerServiceImpl singerService;
    @Mock
    private AlbumServiceImpl albumService;
    @Mock
    private AuthorRepositoryImpl authorRepository;
    @InjectMocks
    private SongMapper songMapper;

    @Test
    void mapEntityFromDto() {
        when(singerService.findSingerByIdWhichWillReturnModel(SINGER.getId())).thenReturn(SINGER);
        when(albumService.findAlbumByIdWhichWillReturnModel(ALBUM.getId())).thenReturn(ALBUM);
        when(authorRepository.findById(AUTHOR.getId())).thenReturn(Optional.of(AUTHOR));

        SongEntity result = songMapper.mapEntityFromDto(generateAuthorDto());

        assertNull(result.getId());
        assertEquals(SONG_NAME, result.getName());
        assertEquals(ALBUM, result.getAlbum());
        assertEquals(SINGER, result.getSinger());
        assertEquals(Arrays.asList(AUTHOR), result.getAuthors());

        verify(singerService).findSingerByIdWhichWillReturnModel(SINGER.getId());
        verify(albumService).findAlbumByIdWhichWillReturnModel(ALBUM.getId());
        verify(authorRepository).findById(AUTHOR.getId());
    }

    @Test
    void mapSongDtoOutFromEntity() {
        SongDtoOut result = songMapper.mapSongDtoOutFromEntity(generateSongEntity());

        assertEquals(ID, result.id());
        assertEquals(SONG_NAME, result.name());
        assertEquals(SINGER.getId(), result.singerId());
        assertEquals(SINGER.getSurname(), result.singerName());
        assertEquals(ALBUM.getId(), result.albumId());
        assertEquals(ALBUM.getAlbumName(), result.albumName());
        assertEquals(AUTHORS.stream().map(AuthorEntity::getAuthorName).collect(toList()), result.authorsName());
    }

    @Test
    void mapSongDtoFromEntity() {
        SongDto result = songMapper.mapSongDtoFromEntity(generateSongEntity());

        assertEquals(SONG_NAME, result.name());
        assertEquals(ALBUM.getId(), result.albumId());
        assertEquals(SINGER.getId(), result.singerId());
        assertEquals(AUTHORS.stream().map(AuthorEntity::getId).collect(toList()), result.authorsId());
    }

    @Test
    void mapListSongDtoFromSongEntity() {
        List<SongEntity> songEntities = Arrays.asList(generateSongEntity(), generateSongEntity());

        List<SongDto> result = songMapper.mapListSongDtoFromSongEntity(songEntities);

        assertEquals(2, result.size());

        for (SongDto dto : result) {
            assertEquals(SONG_NAME, dto.name());
            assertEquals(ALBUM.getId(), dto.albumId());
            assertEquals(SINGER.getId(), dto.singerId());
            assertEquals(AUTHORS.stream().map(AuthorEntity::getId).collect(toList()), dto.authorsId());
        }
    }

    private SongDto generateAuthorDto() {
        List<Long> authorsId = new ArrayList<>();
        authorsId.add(AUTHOR.getId());
        return new SongDto(
                SONG_NAME,
                1L,
                1L,
                authorsId);
    }

    private SongEntity generateSongEntity() {
        return new SongEntity(
                ID,
                SONG_NAME,
                SINGER,
                ALBUM,
                AUTHORS);
    }
}