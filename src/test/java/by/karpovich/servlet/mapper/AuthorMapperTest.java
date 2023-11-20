package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorDtoOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "AuthorTestName";
    private static final String SONG_NAME = "SongTestName";
    private static final SongEntity SONG = new SongEntity(1L, "SongTestName");
    private static final List<SongEntity> SONGS = Arrays.asList(SONG);

    @Mock
    private SongRepositoryImpl songRepository;
    @InjectMocks
    private AuthorMapper authorMapper;

    @Test
    void mapEntityFromDto() {
        AuthorEntity result = authorMapper.mapEntityFromDto(generateAuthorDto());

        assertNull(result.getId());
        assertEquals(AUTHOR_NAME, result.getAuthorName());
    }

    @Test
    void mapDtoFromEntity() {
        AuthorDto result = authorMapper.mapDtoFromEntity(generateAuthorEntity());

        assertEquals(AUTHOR_NAME, result.name());
    }

    @Test
    void mapFullDtoFromEntity() {
        when(songRepository.findByAuthorId(ID)).thenReturn(SONGS);

        AuthorDtoOut result = authorMapper.mapDtoOutFromEntity(generateAuthorEntity());

        assertEquals(AUTHOR_NAME, result.name());
        assertEquals(SONGS.stream().map(SongEntity::getName).collect(toList()), result.songsName());
    }

    @Test
    void mapListDtoFromListEntity() {
        List<AuthorEntity> authorEntities = Arrays.asList(generateAuthorEntity(), generateAuthorEntity());
        List<AuthorDto> result = authorMapper.mapListDtoFromListEntity(authorEntities);

        assertEquals(2, result.size());

        for (AuthorDto dto : result) {
            assertEquals(AUTHOR_NAME, dto.name());
        }
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto(AUTHOR_NAME);
    }

    private AuthorEntity generateAuthorEntity() {
        return new AuthorEntity(
                ID,
                AUTHOR_NAME,
                SONGS);
    }

    private SongEntity generateSongEntity() {
        return new SongEntity(SONG_NAME);
    }
}