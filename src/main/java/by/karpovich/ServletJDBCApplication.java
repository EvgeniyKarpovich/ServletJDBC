package by.karpovich;

import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;

import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();

        SongDtoOut byId = songService.findByIdOUT(8L);
        System.out.println(byId);

    }
}
