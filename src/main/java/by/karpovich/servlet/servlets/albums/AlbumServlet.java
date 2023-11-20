package by.karpovich.servlet.servlets.albums;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.JShell;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/albums")
public class AlbumServlet extends HttpServlet {

    private AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Long.valueOf(req.getParameter("id"));
        if (id != null) {
            AlbumDto dto = albumService.findById(id);
            if (dto != null) {
                String data = String.format("Name: %s SingerId: %s", dto.name(), dto.singerId());
                resp.getWriter().write(data);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long singerId = Long.valueOf(req.getParameter("singerId"));

        AlbumDto albumDto = new AlbumDto(name, singerId);
        AlbumDto savedAlbumDto = albumService.save(albumDto);

        if (savedAlbumDto != null) {
            String data = String.format("Name: %s SingerId: %s", savedAlbumDto.name(), savedAlbumDto.singerId());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long singerId = Long.valueOf(req.getParameter("singerId"));
        Long id = Long.valueOf(req.getParameter("id"));

        AlbumDto albumDto = new AlbumDto(name, singerId);
        albumService.update(albumDto, id);

        if (albumDto != null) {
            String data = String.format("Name: %s SingerId: %s", albumDto.name(), albumDto.singerId());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        if (id != null) {
            albumService.deleteById(id);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
