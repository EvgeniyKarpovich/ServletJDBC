package by.karpovich.service.impl;

import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.service.AuthorService;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.mapper.AuthorMapper;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private static final AuthorServiceImpl INSTANCE = new AuthorServiceImpl();
    private final AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();
    private final AuthorMapper authorMapper = new AuthorMapper();

    private AuthorServiceImpl() {
    }

    public static AuthorServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public AuthorDto findById(Long id) {
        return null;
    }

    @Override
    public List<AuthorDto> findAll() {
        return null;
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
        AuthorEntity authorEntity = authorMapper.mapEntityFromDto(dto);
        AuthorEntity saved = authorRepository.save(authorEntity);

        return authorMapper.mapDtoFromEntity(saved);
    }

    @Override
    public void update(AuthorDto dto, Long id) {
        AuthorEntity authorEntity = authorMapper.mapEntityFromDto(dto);
        authorEntity.setId(id);
        AuthorEntity save = authorRepository.save(authorEntity);
    }
}