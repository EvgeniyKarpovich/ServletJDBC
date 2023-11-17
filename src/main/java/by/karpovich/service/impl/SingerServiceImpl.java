package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.service.SingerService;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerFullDtoOut;
import by.karpovich.servlet.mapper.SingerMapper;

import java.util.List;
import java.util.Optional;

public class SingerServiceImpl implements SingerService {

    private static final SingerServiceImpl INSTANCE = new SingerServiceImpl();
    private final SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
    private final SingerMapper singerMapper = new SingerMapper();

    private SingerServiceImpl() {
    }

    public static SingerServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public SingerDto findById(Long id) {
        SingerEntity singerEntity = singerRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Singer with id = %s not found", id)));
        return singerMapper.mapDtoFromEntity(singerEntity);
    }

    public SingerFullDtoOut findByIdReturnFullDto(Long id) {
        SingerEntity singerEntity = singerRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Singer with id = %s not found", id)));
        return singerMapper.mapFullDtoOutFromEntity(singerEntity);
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

    public SingerEntity findSingerByIdWhichWillReturnModel(Long id) {
        return singerRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Singer with id = %s not found", id)));
    }

    private void validateAlreadyExists(SingerDto dto, Long id) {
        Optional<SingerEntity> entity = singerRepository.findByName(dto.surname());
        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Song with name = %s already exist", dto.surname()));
        }
    }
}

