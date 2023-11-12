package by.karpovich.servlet.mapper;

import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;

import java.util.Optional;

public class AlbumMapper {

    private SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
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
}
