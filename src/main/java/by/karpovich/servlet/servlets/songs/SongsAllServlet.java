package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/songs/all")
public class SongsAllServlet extends HttpServlet {

    private final SongServiceImpl songService = SongServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SongDto> all = songService.findAll();
        for (SongDto dto : all) {
            String data = String.format("Name: %s SingerId: %s albumId: %s AuthorsId %s\n", dto.name(), dto.singerId(), dto.albumId(), dto.authorsId());
            resp.getWriter().write(data);
        }
    }
}
