package by.karpovich;

import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.impl.SingerRepositoryImpl;
import by.karpovich.repository.impl.SongRepositoryImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServletJDBCApplication {

    public static void main(String[] args)   {

        SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();
        SingerEntity singerEntity = new SingerEntity();
        singerEntity.setName("Eugene");

        SongEntity songEntity = new SongEntity();
        songEntity.setName("EEEE");
        songEntity.setSingers(singerEntity);

        songRepository.update(songEntity);

        System.out.println(songEntity);



//        singerRepository.save(singerEntity);
//
//        Optional<SingerEntity> maybeSinger = singerRepository.findById(1L);
//        System.out.println(maybeSinger.get());
//
//        maybeSinger.ifPresent(singer -> {
//            singer.setName("BIG Eugene");
//            singerRepository.update(singer);
//        });
//
//        System.out.println(maybeSinger);
//
//        List<SingerEntity> all = singerRepository.findAll();
//        System.out.println(all);

    }
}
