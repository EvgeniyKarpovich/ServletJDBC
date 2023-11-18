package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorDtoOut;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class AuthorMapper {
    private SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

    public AuthorEntity mapEntityFromDto(AuthorDto dto) {
        return Optional.ofNullable(dto)
                .map(authorDto -> new AuthorEntity(
                        authorDto.name()/*,
                        songRepository.findByAuthorName(authorDto.name())*/))
                .orElse(null);
    }

    public AuthorDto mapDtoFromEntity(AuthorEntity entity) {
        return Optional.ofNullable(entity)
                .map(authorEntity ->
                        new AuthorDto(authorEntity.getAuthorName()))
                .orElse(null);
    }

    public AuthorDtoOut mapFullDtoFromEntity(AuthorEntity entity) {
        return Optional.ofNullable(entity)
                .map(authorEntity ->
                        new AuthorDtoOut(
                                authorEntity.getAuthorName(),
                                songsNameByAuthorId(authorEntity.getId())))
                .orElse(null);
    }

    public List<AuthorDto> mapListDtoFromListEntity(List<AuthorEntity> entities) {
        return Optional.ofNullable(entities)
                .map(authorEntities -> authorEntities.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(toList()))
                .orElse(null);
    }

    private List<String> songsNameByAuthorId(Long authorId) {
        return songRepository.findByAuthorId(authorId).stream()
                .map(SongEntity::getName).collect(toList());
    }
}
