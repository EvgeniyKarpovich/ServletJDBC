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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {

    private static final Long ID = 1L;
    private final static String AUTHOR_NAME = "AuthorTestName";
    private final static String SONG_NAME = "SongTestName";

    @Mock
    private SongRepositoryImpl songRepository;
    @InjectMocks
    private AuthorMapper authorMapper;

    @Test
    void mapEntityFromDto() {
        AuthorDto authorDto = generateAuthorDto();

        AuthorEntity mappedEntity = authorMapper.mapEntityFromDto(authorDto);

        assertEquals(authorDto.name(), mappedEntity.getAuthorName());
    }

    @Test
    void mapDtoFromEntity() {
        AuthorEntity authorEntity = generateAuthorEntity();

        AuthorDto authorDto = authorMapper.mapDtoFromEntity(authorEntity);

        assertEquals(authorEntity.getAuthorName(), authorDto.name());
    }

    @Test
    void mapFullDtoFromEntity() {
        AuthorEntity authorEntity = generateAuthorEntity();

        SongEntity songEntity = generateSongEntity();

        when(songRepository.findByAuthorId(ID)).thenReturn(Arrays.asList(songEntity));

        AuthorDtoOut expectedDto = new AuthorDtoOut(AUTHOR_NAME, Arrays.asList(SONG_NAME));
        AuthorDtoOut resultDto = authorMapper.mapFullDtoFromEntity(authorEntity);

        assertEquals(expectedDto, resultDto);
    }

    @Test
    void mapListDtoFromListEntity() {
        AuthorEntity authorEntity = generateAuthorEntity();

        List<AuthorEntity> authorEntities = Arrays.asList(authorEntity);

        AuthorDto expectedDto = generateAuthorDto();
        List<AuthorDto> expectedResult = Arrays.asList(expectedDto);

        assertEquals(expectedResult, authorMapper.mapListDtoFromListEntity(authorEntities));
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto(AUTHOR_NAME);
    }

    private AuthorEntity generateAuthorEntity() {
        return new AuthorEntity(
                ID,
                AUTHOR_NAME);
    }

    private SongEntity generateSongEntity() {
        return new SongEntity(SONG_NAME);
    }
}