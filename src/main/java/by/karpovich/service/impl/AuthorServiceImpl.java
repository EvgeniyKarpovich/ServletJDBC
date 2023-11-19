package by.karpovich.service.impl;

import by.karpovich.exception.DuplicateException;
import by.karpovich.exception.NotFoundEntityException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.service.AuthorService;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorDtoOut;
import by.karpovich.servlet.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private static final AuthorServiceImpl INSTANCE = new AuthorServiceImpl();
    private AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();
    private AuthorMapper authorMapper = new AuthorMapper();

    private AuthorServiceImpl() {
    }

    public static AuthorServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public AuthorDto findById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Author with id = %s not found", id)));

        return authorMapper.mapDtoFromEntity(authorEntity);
    }

    @Override
    public AuthorDtoOut findByIdFullDtoOut(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(String.format("Author with id = %s not found", id)));

        return authorMapper.mapFullDtoFromEntity(authorEntity);
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorMapper.mapListDtoFromListEntity(authorRepository.findAll());
    }

    @Override
    public boolean deleteById(Long id) {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public AuthorDto save(AuthorDto dto) {
        validateAlreadyExists(dto, null);

        AuthorEntity authorEntity = authorMapper.mapEntityFromDto(dto);
        AuthorEntity saved = authorRepository.save(authorEntity);

        return authorMapper.mapDtoFromEntity(saved);
    }

    @Override
    public void update(AuthorDto dto, Long id) {
        validateAlreadyExists(dto, id);

        AuthorEntity authorEntity = authorMapper.mapEntityFromDto(dto);
        authorEntity.setId(id);

        authorRepository.save(authorEntity);
    }

    private void validateAlreadyExists(AuthorDto dto, Long id) {
        Optional<AuthorEntity> entity = authorRepository.findByAuthorName(dto.name());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Author with name = %s already exist", dto.name()));
        }
    }
}
