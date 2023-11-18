package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.servlet.dto.AuthorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {

    private static final Long ID = 1L;
    private final static String AUTHOR_NAME = "AuthorTestName";
    private final static String SONG_NAME = "SongTestName";

    @InjectMocks
    private final AuthorMapper authorMapper = new AuthorMapper();

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
    }

    @Test
    void mapListDtoFromListEntity() {
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto(AUTHOR_NAME);
    }

    private AuthorEntity generateAuthorEntity() {
        return new AuthorEntity(AUTHOR_NAME);
    }
}