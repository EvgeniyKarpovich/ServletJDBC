package by.karpovich.service;

import by.karpovich.servlet.dto.SingerDto;

import java.util.List;

public interface SingerService extends BaseService<SingerDto, Long> {

    SingerDto findById(Long id);

    List<SingerDto> findAll();

    boolean deleteById(Long id);

    SingerDto save(SingerDto t);

    void update(SingerDto t, Long k);
}
