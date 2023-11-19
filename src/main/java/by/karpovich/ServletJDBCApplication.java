package by.karpovich;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.AlbumRepositoryImpl;
import by.karpovich.repository.impl.AuthorRepositoryImpl;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SingerDtoOut;
import by.karpovich.servlet.dto.SongDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();
        AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

        AlbumDto byId = albumService.findById(1L);

        AlbumDto albumDto = new AlbumDto("FINAL", 2L);

        SingerDto singerDto = new SingerDto("Gomonchuk");
//        SingerEntity singerEntity = new SingerEntity("OOOOO");
//        SingerEntity save = singerRepository.save(singerEntity);
//
//        AlbumEntity albumEntity = new AlbumEntity("ALBUUUUUM", singerEntity);
//        AlbumEntity save1 = albumRepository.save(albumEntity);
//        AuthorEntity authorEntity = new AuthorEntity("AUTHOOOOOOOR");
//        AuthorEntity save2 = authorRepository.save(authorEntity);
//        List<AuthorEntity> authorEntities = new ArrayList<>();
//        authorEntities.add(save2);
//
//        SongEntity songEntity = new SongEntity("FINAL FINAL SOOOOONG", save, save1,authorEntities);
//        SongEntity save3 = songRepository.save(songEntity);
//        System.out.println(save3);

        songService.save(new SongDto("ijzkjasl", 2L, 2L, Arrays.asList(1L)));
    }
}
