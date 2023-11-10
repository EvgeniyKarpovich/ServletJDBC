package by.karpovich;

import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SongDto;

import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args)   {
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        System.out.println(songRepository.findAll());
    }
}
