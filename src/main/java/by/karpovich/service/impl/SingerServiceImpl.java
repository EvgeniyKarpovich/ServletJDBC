package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.mapper.SingerMapper;

import java.util.List;
import java.util.Optional;

public class SingerServiceImpl implements by.karpovich.service.SingerService<SingerDto, Long> {

    private static SingerServiceImpl instance;
    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
    private final SingerMapper singerMapper = SingerMapper.getInstance();

    private SingerServiceImpl() {
        // Private constructor to prevent instantiation.
    }

    public static SingerServiceImpl getInstance() {
        if (instance == null) {
            return instance = new SingerServiceImpl();
        }
        return instance;
    }

    @Override
    public SingerDto findById(Long id) {
        SingerEntity singerEntity = singerRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException("IN SERVICE FIND BY ID")
        );

        return singerMapper.mapDtoFromEntity(singerEntity);
    }

    @Override
    public List<SingerDto> findAll() {
        return singerMapper.mapListDtoFromListEntity(singerRepository.findAll());
    }

    @Override
    public boolean deleteById(Long id) {
        if (singerRepository.findById(id).isPresent()) {
            singerRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public SingerDto save(SingerDto singerDto) {
        validateAlreadyExists(singerDto, null);

        SingerEntity singerEntity = singerMapper.mapEntityFromDto(singerDto);
        SingerEntity save = singerRepository.save(singerEntity);
        return singerMapper.mapDtoFromEntity(save);
    }

    @Override
    public void update(SingerDto singerDto, Long id) {
        validateAlreadyExists(singerDto, id);
        SingerEntity singerEntity = singerMapper.mapEntityFromDto(singerDto);
        singerEntity.setId(id);
        singerRepository.update(singerEntity);
    }

    private void validateAlreadyExists(SingerDto singerDto, Long id) {
        Optional<SingerEntity> entity = singerRepository.findByName(singerDto.name());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException("IN validateAlreadyExists");
        }
    }
}

