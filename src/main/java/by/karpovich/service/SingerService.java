package by.karpovich.service;

import by.karpovich.model.SingerEntity;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerDtoOut;

public interface SingerService extends BaseService<SingerDto, Long> {

    SingerDtoOut findByIdReturnFullDto(Long id);

    SingerEntity findSingerByIdWhichWillReturnModel(Long id);
}
