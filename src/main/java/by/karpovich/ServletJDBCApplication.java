package by.karpovich;

import by.karpovich.model.AlbumEntity;
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
import by.karpovich.servlet.dto.*;

import java.util.ArrayList;
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

        AuthorDto authorDto = new AuthorDto("Karpovich MEGA BUM 22");
//        authorService.save(authorDto);
        SingerDto singerDto = new SingerDto("Kaprovich SInger MEGA 22");
//        singerService.save(singerDto);
        AlbumDto albumDto = new AlbumDto("Second album 22", 1L);
//        albumService.save(albumDto);
        List<Long> authors = new ArrayList<>();
        authors.add(1L);
//        authors.add(9L);

        SongDto songDto = new SongDto("way 32312!!!", 2L, 1L, authors);
        songService.save(songDto);
//        SongDtoOut byIdFullDtoOut = songService.findByIdFullDtoOut(7L);
//        System.out.println(byIdFullDtoOut);
//        Optional<SongEntity> byId = songRepository.findById(1L);
//        System.out.println(byId.get());

        // author find by id/findAll
//        AuthorEntity authorEntity = new AuthorEntity("Evgeniy Karpovich");
//        AuthorEntity authorEntity2 = new AuthorEntity("Artem Shpak");
//
//        AuthorEntity save = authorRepository.save(authorEntity);
//        AuthorEntity save2 = authorRepository.save(authorEntity2);
//
//        List<AuthorEntity> authorEntities = new ArrayList<>();
//        authorEntities.add(save);
//        authorEntities.add(save2);

//        SingerEntity singerEntity = new SingerEntity("EMINEM");
//        SingerEntity singerEntity2 = new SingerEntity("ADEL");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity);
//        SingerEntity saveSingerEntity2 = singerRepository.save(singerEntity2);

//        SingerEntity singerEntity2 = new SingerEntity("ADEL FOR ALBUM");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity2);
//
//        AlbumEntity albumEntity = new AlbumEntity("KKKK", singerEntity2);
//        AlbumEntity saveAlbumEntity = albumRepository.save(albumEntity);
//
//        AuthorEntity authorEntity = new AuthorEntity("Evgeniy Karpovich22222");
//        AuthorEntity authorEntity2 = new AuthorEntity("Artem Shpak22222");
//
//        AuthorEntity save = authorRepository.save(authorEntity);
//        AuthorEntity save2 = authorRepository.save(authorEntity2);
//
//        List<AuthorEntity> authorEntities = new ArrayList<>();
//        authorEntities.add(save);
//        authorEntities.add(save2);
//        Optional<SongEntity> byId2 = songRepository.findById(2L);
//
//        List<SongEntity> songEntities = new ArrayList<>();
//        songEntities.add(byId2.get());


//        AlbumEntity albumEntity = new AlbumEntity("NEW ALBUM", save1, entities);
//        AlbumEntity save2 = albumRepository.save(albumEntity);

//        System.out.println(save2);


//        SingerEntity singerEntity = new SingerEntity("&&&&&&&");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity);
//        AlbumEntity albumEntity = new AlbumEntity(".........", singerEntity);
//        AlbumEntity saveAlbumEntity = albumRepository.save(albumEntity);
//
//        SongEntity songEntity = new SongEntity("EUGENE KARPOVICH", saveSingerEntity, saveAlbumEntity, authorEntities);
//        songRepository.save(songEntity);
//        Optional<SongEntity> byId = songRepository.findById(5L);
//        Optional<SongEntity> byId2 = songRepository.findById(1L);
//        SongEntity songEntity = byId.get();
//        SongEntity songEntity1 = byId2.get();
//
//        List<SongEntity> songEntities = new ArrayList<>();
//        songEntities.add(songEntity);
//        songEntities.add(songEntity1);
//
//
//        AuthorEntity authorEntity = new AuthorEntity("11111111112222222", songEntities);
//        authorRepository.save(authorEntity);

    }
}
