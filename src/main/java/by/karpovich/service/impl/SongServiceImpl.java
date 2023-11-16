package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.SongService;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongFullDtoOut;
import by.karpovich.servlet.mapper.SongMapper;

import java.util.List;
import java.util.Optional;

public class SongServiceImpl implements SongService {

    private static final SongServiceImpl INSTANCE = new SongServiceImpl();
    private final SongMapper songMapper = new SongMapper();
    private final SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

    private SongServiceImpl() {
    }

    public static SongServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public SongDto findById(Long id) {
        SongEntity songEntity = songRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE FIND BY ID"));

        return songMapper.mapSongDtoFromEntity(songEntity);
    }

    @Override
    public SongFullDtoOut findByIdFullDtoOut(Long id) {
        SongEntity songEntity = songRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE FIND BY ID "));

        return songMapper.mapSongDtoOutFromEntity(songEntity);
    }

    @Override
    public List<SongDto> findAll() {
        return songMapper.mapListSongDtoFromSongEntity(songRepository.findAll());
    }

    @Override
    public boolean deleteById(Long id) {
        if (songRepository.findById(id).isPresent()) {
            songRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public SongDto save(SongDto dto) {
        validateAlreadyExists(dto, null);

        SongEntity songEntity = songMapper.mapEntityFromDto(dto);
        SongEntity save = songRepository.save(songEntity);

        return songMapper.mapSongDtoFromEntity(save);
    }

    @Override
    public void update(SongDto dto, Long id) {
        validateAlreadyExists(dto, id);

        SongEntity entity = songMapper.mapEntityFromDto(dto);
        entity.setId(id);
        songRepository.update(entity);
    }

    private void validateAlreadyExists(SongDto dto, Long id) {
        Optional<SongEntity> entity = songRepository.findByName(dto.name(), dto.singerId());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException("IN validateAlreadyExists");
        }
    }
}
