package by.karpovich;

import by.karpovich.service.SongService;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.dto.SongDto;

public class ServletJDBCApplication {

    public static void main(String[] args) {
        SingerServiceImpl singerService = SingerServiceImpl.getInstance();

        SingerDto singerDto = new SingerDto("!!!!!!");

        SongService songService = SongServiceImpl.getInstance();
        SongDto byId = songService.findById(8L);
        System.out.println(byId);


//        SongDto byId = songService.findById(8L);

        songService.update(byId, 28L);
        System.out.println();
    }
}
