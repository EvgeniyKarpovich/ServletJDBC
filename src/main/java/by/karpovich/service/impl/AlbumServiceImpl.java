package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.AlbumEntity;
import by.karpovich.repository.impl.AlbumRepositoryImpl;
import by.karpovich.service.AlbumService;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.mapper.AlbumMapper;

import java.util.List;
import java.util.Optional;

public class AlbumServiceImpl implements AlbumService {

    private static final AlbumServiceImpl INSTANCE = new AlbumServiceImpl();
    private AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
    private AlbumMapper albumMapper = new AlbumMapper();

    private AlbumServiceImpl() {
    }

    public static AlbumServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public AlbumDto findById(Long id) {
        AlbumEntity albumEntity = albumRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Album with id = %s not found", id)));

        return albumMapper.mapDtoFromEntity(albumEntity);
    }

    @Override
    public List<AlbumDto> findAll() {
        return albumMapper.mapListDtoFromListEntity(albumRepository.findAll());
    }

    @Override
    public boolean deleteById(Long id) {
        if (albumRepository.findById(id).isPresent()) {
            albumRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public AlbumDto save(AlbumDto dto) {
        validateAlreadyExists(dto, null);

        AlbumEntity albumEntity = albumMapper.mapEntityFromDto(dto);
        AlbumEntity saved = albumRepository.save(albumEntity);

        return albumMapper.mapDtoFromEntity(saved);
    }

    @Override
    public void update(AlbumDto dto, Long id) {
        AlbumEntity albumEntity = albumMapper.mapEntityFromDto(dto);
        albumEntity.setId(id);

        albumRepository.update(albumEntity);
    }

    public AlbumEntity findSingerByIdWhichWillReturnModel(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Album with id = %s not found", id)));
    }

    private void validateAlreadyExists(AlbumDto dto, Long id) {
        Optional<AlbumEntity> entity = albumRepository.findByNameAndSingerId(dto.name(), dto.singerId());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Song with name = %s already exist", dto.name()));
        }
    }
}
