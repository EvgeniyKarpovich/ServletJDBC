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
import by.karpovich.servlet.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();

//        AuthorEntity authorEntity = new AuthorEntity("SHEKSPIR");
//        AuthorEntity authorEntity2 = new AuthorEntity("SHEKSPIR333");
//
//        AuthorEntity save = authorRepository.save(authorEntity);
//        AuthorEntity save2 = authorRepository.save(authorEntity2);
//
//        List<AuthorEntity> authorEntities = new ArrayList<>();
//        authorEntities.add(save);
//        authorEntities.add(save2);
//
//        SingerEntity singerEntity = new SingerEntity("50 CENT");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity);
//
//        AlbumEntity albumEntity = new AlbumEntity("KKKK", singerEntity);
//        AlbumEntity saveAlbumEntity = albumRepository.save(albumEntity);
//
//        SongEntity songEntity = new SongEntity("KKKK", saveSingerEntity, saveAlbumEntity, authorEntities);
//
//        SongEntity save1 = songRepository.save(songEntity);
//        System.out.println(save1);

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();
        AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

        songRepository.findByAuthorName("KKKK");

        List<Long> songsId = new ArrayList<>();
        songsId.add(1L);
        songsId.add(5L);
        AuthorDto authorDto = new AuthorDto("BOSS", songsId);
//        AuthorDto save = authorService.save(authorDto);
//        System.out.println(save);


//        SingerEntity singerEntity = new SingerEntity("50 CENT");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity);
//        AlbumEntity albumEntity = new AlbumEntity("OPOPOPOPO", singerEntity);
//        AlbumEntity saveAlbumEntity = albumRepository.save(albumEntity);
//        SongEntity songEntity = new SongEntity("NANANNANA", saveSingerEntity, saveAlbumEntity);
//        songRepository.save(songEntity);


    }
}
