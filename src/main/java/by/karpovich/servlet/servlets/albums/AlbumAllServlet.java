package by.karpovich.servlet.servlets.albums;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/albums/all")
public class AlbumAllServlet extends HttpServlet {

    private final AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AlbumDto> allDto = albumService.findAll();
        if (allDto != null) {
            for (AlbumDto dto : allDto) {
                String data = String.format("Name: %s SingerId: %s\n", dto.name(), dto.singerId());
                resp.getWriter().write(data);
            }
        }
    }
}
