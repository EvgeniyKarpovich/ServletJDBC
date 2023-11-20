package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDtoOut;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/songs/out")
public class SongsOutServlet extends HttpServlet {

    private final SongServiceImpl songService = SongServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        SongDtoOut dto = songService.findByIdFullDtoOut(id);
        if (dto != null) {
            String data = String.format("Name: %s SingerId: %s SingerName: %s albumId: %s AlbumName : %s  AuthorsName %s",
                    dto.name(), dto.singerId(), dto.singerName(), dto.albumId(), dto.albumName(), dto.authorsName());
            resp.getWriter().write(data);
        }
    }
}