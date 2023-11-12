package by.karpovich.service.impl;

import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.impl.AlbumRepositoryImpl;
import by.karpovich.service.AlbumService;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.mapper.AlbumMapper;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private static final AlbumServiceImpl INSTANCE = new AlbumServiceImpl();
    private final AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
    private final AlbumMapper albumMapper = new AlbumMapper();

    private AlbumServiceImpl() {
    }

    public static AlbumServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public AlbumDto findById(Long id) {
        AlbumEntity albumEntity = albumRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE FIND BY ID"));

        return albumMapper.mapDtoFromEntity(albumEntity);
    }

    @Override
    public List<AlbumDto> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public AlbumDto save(AlbumDto dto) {
        AlbumEntity albumEntity = albumMapper.mapEntityFromDto(dto);
        AlbumEntity saved = albumRepository.save(albumEntity);

        return albumMapper.mapDtoFromEntity(saved);
    }

    @Override
    public void update(AlbumDto dto, Long aLong) {

    }

    public AlbumEntity findSingerByIdWhichWillReturnModel(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE findSingerByIdWhichWillReturnModel"));
    }
}
