package by.karpovich;

import by.karpovich.repository.impl.SongRepositoryImpl;
import by.karpovich.service.SingerService;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SingerDto;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        SongRepositoryImpl songRepository = SongRepositoryImpl.getInstance();

        SingerServiceImpl singerService = SingerServiceImpl.getInstance();

        SingerDto singerDto = new SingerDto("new Eugene");
//        SingerDto save = singerService.save(singerDto);
//        System.out.println(save);

        System.out.println(singerService.findById(3L));
        System.out.println(singerService.findAll());

        SingerDto update = new SingerDto("UPDATE DTO");
        singerService.update(update, 3L);

    }

}
