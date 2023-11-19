package by.karpovich.service;

import by.karpovich.model.AlbumEntity;
import by.karpovich.servlet.dto.AlbumDto;

public interface AlbumService extends BaseService<AlbumDto, Long> {

    AlbumEntity findAlbumByIdWhichWillReturnModel(Long id);
}
