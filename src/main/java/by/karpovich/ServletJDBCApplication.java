package by.karpovich;

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

        AuthorDto authorDto = new AuthorDto("Karpovich MEGA BdsdUM 223232");
        AuthorDto authorDto3 = new AuthorDto("Karpovich MEGA BUM 33");
//        authorService.save(authorDto);
//        authorService.save(authorDto3);
        SingerDto singerDto = new SingerDto("Kaprovich TEST");
//        singerService.save(singerDto);
        AlbumDto albumDto = new AlbumDto("Second album 22", 1L);
        AlbumDto albumDto2 = new AlbumDto("!!!! album 22", 1L);
//        albumService.save(albumDto2);
        List<Long> authors = new ArrayList<>();
        authors.add(1L);
        authors.add(2L);

        SongDto songDto = new SongDto("3213123121", 1L, 1L, authors);

        SingerDtoOut byIdReturnFullDto = singerService.findByIdReturnFullDto(1L);
        System.out.println(byIdReturnFullDto);

        List<AuthorDto> all = authorService.findAll();
        System.out.println(all);
    }
}
