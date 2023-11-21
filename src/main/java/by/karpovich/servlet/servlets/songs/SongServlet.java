package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import com.google.gson.Gson;
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

    private SongServiceImpl songService = SongServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        Long id = Long.valueOf(req.getParameter("id"));

        SongDto dto = songService.findById(id);
        if (dto != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(dto, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

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
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(result, resp.getWriter());
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
        Long id = Long.valueOf(req.getParameter("id"));

        SongDto dto = new SongDto(name, singerId, albumId, authors);
        songService.update(dto, id);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        songService.deleteById(id);
    }
}
