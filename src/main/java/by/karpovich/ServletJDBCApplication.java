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
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SongDto;

import java.util.ArrayList;
import java.util.List;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        AuthorRepositoryImpl authorRepository = AuthorRepositoryImpl.getInstance();

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


        SingerEntity singerEntity = new SingerEntity("44444");
        SingerEntity save1 = singerRepository.save(singerEntity);

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();
        AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

        SongEntity songEntity = new SongEntity("NEW SONG", save1);
//        SongEntity save = songRepository.save(songEntity);

        AlbumDto albumDto = new AlbumDto("ALBUN MEGA POP TRASH", 2L);
        //ОСТАЛСЯ FIND ALL в АЛЬБУМ
        List<AlbumDto> all = albumService.findAll();
        System.out.println(all);

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
