package by.karpovich.servlet.mapper;

import by.karpovich.model.SingerEntity;
import by.karpovich.servlet.dto.SingerDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingerMapper {

    public SingerEntity mapEntityFromDto(SingerDto dto) {
        return Optional.ofNullable(dto)
                .map(singerDto -> new SingerEntity(singerDto.name())
                )
                .orElse(null);
    }

    public SingerDto mapDtoFromEntity(SingerEntity entity) {
        return Optional.ofNullable(entity)
                .map(singerEntity ->
                        new SingerDto(entity.getSurname())).orElse(null);
    }

    public List<SingerDto> mapListDtoFromListEntity(List<SingerEntity> entities) {
        return Optional.ofNullable(entities)
                .map(listEntity -> entities.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
