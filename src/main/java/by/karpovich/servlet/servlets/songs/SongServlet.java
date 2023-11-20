package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/songs")
public class SongServlet extends HttpServlet {

    private final SongServiceImpl songService = SongServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        SongDto dto = songService.findById(id);
        if (dto != null) {
            String data = String.format("Name: %s SingerId: %s albumId: %s AuthorsId %s", dto.name(), dto.singerId(), dto.albumId(), dto.authorsId());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long singerId = Long.parseLong(req.getParameter("singerId"));
        Long albumId = Long.parseLong(req.getParameter("albumId"));

        String[] authorsStrings = req.getParameter("authorsId").split(",");
        List<Long> authors = new ArrayList<>();
        for (String author : authorsStrings) {
            authors.add(Long.parseLong(author.trim()));
        }

        SongDto dto = new SongDto(name, singerId, albumId, authors);
        SongDto result = songService.save(dto);

        if (result != null) {
            String data = String.format("Name: %s", result.name());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long singerId = Long.parseLong(req.getParameter("singerId"));
        Long albumId = Long.parseLong(req.getParameter("albumId"));

        String[] authorsStrings = req.getParameter("authorsId").split(",");
        List<Long> authors = new ArrayList<>();
        for (String author : authorsStrings) {
            authors.add(Long.parseLong(author));
        }

        SongDto dto = new SongDto(name, singerId, albumId, authors);
        SongDto result = songService.save(dto);

        if (result != null) {
            String data = String.format("Name: %s", result.name());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        songService.deleteById(id);
    }
}
