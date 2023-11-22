package by.karpovich.servlet.servlets.albums;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/albums")
public class AlbumServlet extends HttpServlet {

    private AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        Long id = Long.valueOf(req.getParameter("id"));

        if (id == null) {
            throw new ServletException("id must not be null");
        }

        AlbumDto dto = albumService.findById(id);
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
        Long singerId = Long.valueOf(req.getParameter("singerId"));

        if (name == null || singerId == null) {
            throw new ServletException("Name and SingerId must not be null");
        }

        AlbumDto albumDto = new AlbumDto(name, singerId);
        AlbumDto dto = albumService.save(albumDto);

        if (dto != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(dto, resp.getWriter());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long singerId = Long.valueOf(req.getParameter("singerId"));
        Long id = Long.valueOf(req.getParameter("id"));

        if (name == null || singerId == null || id == null) {
            throw new ServletException("Name and SingerId must not be null");
        }

        AlbumDto albumDto = new AlbumDto(name, singerId);
        albumService.update(albumDto, id);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        if (id == null) {
            throw new ServletException("id must not be null");
        }

        albumService.deleteById(id);
    }
}
