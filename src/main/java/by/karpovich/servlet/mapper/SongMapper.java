package by.karpovich.servlet.mapper;

import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SongMapper {
    private SingerServiceImpl singerService = SingerServiceImpl.getInstance();
    private AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
    private AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();

    public SongEntity mapEntityFromDto(SongDto dto) {
        return Optional.ofNullable(dto)
                .map(songDto ->
                        new SongEntity(songDto.name(),
                                singerService.findSingerByIdWhichWillReturnModel(songDto.singerId()),
                                albumService.findAlbumByIdWhichWillReturnModel(songDto.albumId()),
                                getAuthorEntities(songDto.authorsId())))
                .orElse(null);
    }

    public SongDtoOut mapSongDtoOutFromEntity(SongEntity entity) {
        return Optional.ofNullable(entity)
                .map(songEntity -> new SongDtoOut(
                        songEntity.getId(),
                        songEntity.getName(),
                        songEntity.getSinger().getId(),
                        songEntity.getSinger().getSurname(),
                        songEntity.getAlbum().getId(),
                        songEntity.getAlbum().getAlbumName(),
                        songEntity.getAuthors().stream().
                                map(AuthorEntity::getAuthorName)
                                .collect(Collectors.toList())))
                .orElse(null);
    }

    public SongDto mapSongDtoFromEntity(SongEntity entity) {
        return Optional.ofNullable(entity)
                .map(songEntity -> new SongDto(
                        songEntity.getName(),
                        songEntity.getSinger().getId(),
                        songEntity.getAlbum().getId(),
                        songEntity.getAuthors().stream()
                                .map(AuthorEntity::getId)
                                .collect(Collectors.toList())))
                .orElse(null);
    }

    public List<SongDto> mapListSongDtoFromSongEntity(List<SongEntity> entities) {
        return Optional.ofNullable(entities)
                .map(listEntity -> listEntity.stream()
                        .map(this::mapSongDtoFromEntity)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public List<AuthorEntity> getAuthorEntities(List<Long> authorsId) {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (Long id : authorsId) {
            authorEntities.add(authorRepository.findById(id).orElse(null));
        }

        return authorEntities;
    }
}
