package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.AuthorDto;

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
                                authorEntity.getAuthorName()/*,
                                authorEntity.getSongs().stream().map(SongEntity::getId).collect(Collectors.toList())*/
                        ))
                .orElse(null);
    }
}
