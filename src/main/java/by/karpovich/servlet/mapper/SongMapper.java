package by.karpovich.servlet.mapper;

import by.karpovich.model.SongEntity;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;

import java.util.Optional;

public class SongMapper {
    private final SingerServiceImpl singerService = SingerServiceImpl.getInstance();

    public SongEntity mapEntityFromDto(SongDto dto) {
        return Optional.ofNullable(dto)
                .map(songDto ->
                        new SongEntity(songDto.name(),
                                singerService.findSingerByIdWhichWillReturnModel(songDto.singerId()))
                )
                .orElse(null);
    }

    public SongDtoOut mapSongDtoOutFromEntity(SongEntity songEntity) {
        return Optional.ofNullable(songEntity)
                .map(entity -> new SongDtoOut(
                        entity.getId(),
                        entity.getName(),
                        entity.getSinger().getSurname()))
                .orElse(null);
    }

    public SongDto mapSongDtoFromEntity(SongEntity songEntity) {
        return Optional.ofNullable(songEntity)
                .map(entity -> new SongDto(
                        entity.getName(),
                        entity.getSinger().getId()
                ))
                .orElse(null);
    }
}
