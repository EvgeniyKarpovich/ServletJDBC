package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlbumMapper {
    private SingerServiceImpl singerService = SingerServiceImpl.getInstance();

    public AlbumEntity mapEntityFromDto(AlbumDto dto) {
        return Optional.ofNullable(dto)
                .map(albumDto ->
                        new AlbumEntity(
                                albumDto.name(),
                                singerService.findSingerByIdWhichWillReturnModel(albumDto.singerId())))
                .orElse(null);
    }

    public AlbumDto mapDtoFromEntity(AlbumEntity entity) {
        return Optional.ofNullable(entity)
                .map(albumEntity ->
                        new AlbumDto(albumEntity.getAlbumName(),
                                albumEntity.getSinger().getId()))
                .orElse(null);
    }

    public List<AlbumDto> mapListDtoFromListEntity(List<AlbumEntity> entities) {
        return Optional.ofNullable(entities)
                .map(listDto -> listDto.stream().map(this::mapDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
