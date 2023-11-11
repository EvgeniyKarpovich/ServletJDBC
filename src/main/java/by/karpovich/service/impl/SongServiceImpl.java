package by.karpovich.service.impl;

import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.BaseService;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;
import by.karpovich.servlet.mapper.SongMapper;

import java.util.List;

public class SongServiceImpl implements BaseService<SongDto, Long> {

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
        return null;
    }

    public SongDtoOut findByIdOUT(Long id) {
        SongEntity songEntity = songRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE FIND BY ID "));

        return songMapper.mapSongDtoOutFromEntity(songEntity);
    }

    @Override
    public List<SongDto> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public SongDto save(SongDto dto) {
        SongEntity songEntity = songMapper.mapEntityFromDto(dto);
        SongEntity save = songRepository.save(songEntity);

        return songMapper.mapSongDtoFromEntity(save);
    }

    @Override
    public void update(SongDto dto, Long aLong) {

    }
}
