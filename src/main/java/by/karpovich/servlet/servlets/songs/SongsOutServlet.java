package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDtoOut;
import com.google.gson.Gson;
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
        Gson gson = new Gson();

        Long id = Long.valueOf(req.getParameter("id"));

        SongDtoOut dto = songService.findByIdFullDtoOut(id);
        if (dto != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(dto, resp.getWriter());
        }
    }
}