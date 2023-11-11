package by.karpovich.servlet.mapper;

import by.karpovich.model.SingerEntity;
import by.karpovich.servlet.dto.SingerDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingerMapper {

    private static final SingerMapper INSTANCE = new SingerMapper();

    private SingerMapper() {
    }

    public static SingerMapper getInstance() {
        return INSTANCE;
    }

    public SingerEntity mapEntityFromDto(SingerDto dto) {
        return Optional.ofNullable(dto)
                .map(singerDto -> {
                    SingerEntity singerEntity = new SingerEntity();
                    singerEntity.setName(dto.name());
                    return singerEntity;
                })
                .orElse(null);
    }

    public SingerDto mapDtoFromEntity(SingerEntity entity) {
        return Optional.ofNullable(entity)
                .map(singerEntity -> {
                    return new SingerDto(
                            entity.getName());
                }).orElse(null);
    }

    public List<SingerDto> mapListDtoFromListEntity(List<SingerEntity> entities) {
        return Optional.ofNullable(entities)
                .map(listDto -> entities.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
