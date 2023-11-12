package by.karpovich;

import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.AlbumRepositoryImpl;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;

import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        AlbumRepositoryImpl albumRepository = AlbumRepositoryImpl.getInstance();
        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();
        AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();
        SongServiceImpl songService = SongServiceImpl.getInstance();


//        SingerEntity singerEntity = new SingerEntity("50 CENT");
//        SingerEntity saveSingerEntity = singerRepository.save(singerEntity);
//        AlbumEntity albumEntity = new AlbumEntity("OPOPOPOPO", singerEntity);
//        AlbumEntity saveAlbumEntity = albumRepository.save(albumEntity);
//        SongEntity songEntity = new SongEntity("NANANNANA", saveSingerEntity, saveAlbumEntity);
//        songRepository.save(songEntity);

        SongDtoOut byId = songService.findByIdFullDtoOut(1L);
        System.out.println(byId);

    }
}
