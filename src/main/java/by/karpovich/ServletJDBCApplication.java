package by.karpovich;

import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.dto.SingerDto;

import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args) {
//        AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
//        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();
        AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

        AlbumDto byId = albumService.findById(1L);

        AlbumDto albumDto = new AlbumDto("FINrererAL", 2L);

        SingerDto singerDto = new SingerDto("Gomonchuk");


        Optional<AuthorEntity> byId1 = authorRepository.findById(1L);
        System.out.println(byId1.get());
    }
}
