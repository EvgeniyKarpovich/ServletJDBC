package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorFullDtoOut;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthorMapper {
    private final SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

    public AuthorEntity mapEntityFromDto(AuthorDto dto) {

        return Optional.ofNullable(dto)
                .map(authorDto -> new AuthorEntity(
                        authorDto.name(),
                        songRepository.findByAuthorName(authorDto.name())))
                .orElse(null);
    }

    public AuthorDto mapDtoFromEntity(AuthorEntity entity) {
        return Optional.ofNullable(entity)
                .map(authorEntity ->
                        new AuthorDto(
                                authorEntity.getAuthorName()
                        ))
                .orElse(null);
    }

    public AuthorFullDtoOut mapFullDtoFromEntity(AuthorEntity entity) {
        return Optional.ofNullable(entity)
                .map(authorEntity ->
                        new AuthorFullDtoOut(
                                authorEntity.getAuthorName(),
                                authorEntity.getSongs().stream().map(SongEntity::getName).collect(Collectors.toList())
                        ))
                .orElse(null);
    }

    public List<AuthorDto> mapListDtoFromListEntity(List<AuthorEntity> entities) {
        return Optional.ofNullable(entities)
                .map(authorEntities -> authorEntities.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);

    }
}
