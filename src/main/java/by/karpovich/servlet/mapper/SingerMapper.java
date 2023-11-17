package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerFullDtoOut;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingerMapper {

    public SingerEntity mapEntityFromDto(SingerDto dto) {
        return Optional.ofNullable(dto)
                .map(singerDto -> new SingerEntity(singerDto.surname()))
                .orElse(null);
    }

    public SingerFullDtoOut mapFullDtoOutFromEntity(SingerEntity entity) {
        return Optional.ofNullable(entity)
                .map(singerEntity -> new SingerFullDtoOut(
                        singerEntity.getId(),
                        singerEntity.getSurname(),
                        singerEntity.getAlbums().stream().map(AlbumEntity::getAlbumName).collect(Collectors.toList())))
                .orElse(null);
    }

    public SingerDto mapDtoFromEntity(SingerEntity entity) {
        return Optional.ofNullable(entity)
                .map(singerEntity ->
                        new SingerDto(singerEntity.getSurname())).orElse(null);
    }

    public List<SingerDto> mapListDtoFromListEntity(List<SingerEntity> entities) {
        return Optional.ofNullable(entities)
                .map(listEntity -> listEntity.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
