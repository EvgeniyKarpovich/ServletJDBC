package by.karpovich.service;

import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;

public interface SongService extends BaseService<SongDto, Long> {

    SongDtoOut findByIdFullDtoOut(Long id);
}
