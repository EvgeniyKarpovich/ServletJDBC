package by.karpovich.service;

import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.dto.AuthorDtoOut;

public interface AuthorService extends BaseService<AuthorDto, Long> {

    AuthorDtoOut findByIdFullDtoOut(Long id);
}
