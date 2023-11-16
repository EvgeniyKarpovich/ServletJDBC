package by.karpovich.service;

import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongFullDtoOut;

public interface SongService extends BaseService<SongDto, Long> {

    SongFullDtoOut findByIdFullDtoOut(Long id);
}
