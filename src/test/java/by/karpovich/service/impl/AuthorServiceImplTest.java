package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorDtoOut;
import by.karpovich.servlet.mapper.AuthorMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    private static final Long ID = 1L;
    @Mock
    private AuthorRepositoryImpl authorRepository;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void findById() {
        AuthorEntity entity = mock(AuthorEntity.class);
        AuthorDto dto = mock(AuthorDto.class);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(authorMapper.mapDtoFromEntity(any(AuthorEntity.class))).thenReturn(dto);

        AuthorDto result = authorService.findById(ID);
        assertNotNull(result);
    }

    @Test
    void findByIdFullDtoOut() {
        AuthorEntity entity = mock(AuthorEntity.class);
        AuthorDtoOut dto = mock(AuthorDtoOut.class);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(authorMapper.mapDtoOutFromEntity(any(AuthorEntity.class))).thenReturn(dto);

        AuthorDtoOut result = authorService.findByIdFullDtoOut(ID);

        assertEquals(result, dto);
    }

    @Test
    void findAll() {
        List<AuthorEntity> entities = new ArrayList<>();
        List<AuthorDto> dtos = new ArrayList<>();

        when(authorRepository.findAll()).thenReturn(entities);
        when(authorMapper.mapListDtoFromListEntity(anyList())).thenReturn(dtos);

        List<AuthorDto> result = authorService.findAll();

        assertArrayEquals(result.toArray(), dtos.toArray());
    }

    @Test
    void deleteById() {
        AuthorEntity entity = mock(AuthorEntity.class);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        authorService.deleteById(ID);
    }

    @Test
    void save() {
        AuthorEntity mapped = mock(AuthorEntity.class);
        AuthorEntity saved = mock(AuthorEntity.class);

        AuthorDto startDto = mock(AuthorDto.class);
        AuthorDto returnedDto = mock(AuthorDto.class);

        when(authorMapper.mapEntityFromDto(any(AuthorDto.class))).thenReturn(mapped);
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(saved);
        when(authorMapper.mapDtoFromEntity(any(AuthorEntity.class))).thenReturn(returnedDto);

        AuthorDto result = authorService.save(startDto);

        assertEquals(result, returnedDto);
    }

    @Test
    void update() {
        AuthorEntity entity = mock(AuthorEntity.class);
        AuthorDto dto = mock(AuthorDto.class);

        when(authorMapper.mapEntityFromDto(any(AuthorDto.class))).thenReturn(entity);

        authorService.update(dto, ID);

        verify(authorRepository).findByAuthorName(dto.name());
        verify(authorMapper).mapEntityFromDto(dto);
        verify(authorRepository).update(entity);
    }

    @Test
    void findByIdReturnFullDtoThrowException() {
        assertThrows(NotFoundEntityException.class, () -> authorService.findByIdFullDtoOut(ID));

        verify(authorRepository).findById(ID);
    }
    @Test
    void findByIdThrowsException() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> authorService.findById(ID));
    }

    @Test
    void saveThrowsException() {
        when(authorRepository.findByAuthorName(anyString())).thenThrow(DuplicateException.class);

        assertThrows(DuplicateException.class, () -> authorRepository.findByAuthorName(anyString()));
    }
}