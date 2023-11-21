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
import java.util.List;

@WebServlet("/albums/all")
public class AlbumAllServlet extends HttpServlet {

    private AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        List<AlbumDto> allDto = albumService.findAll();
        if (allDto != null) {
            for (AlbumDto dto : allDto) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                gson.toJson(dto, resp.getWriter());
            }
        }
    }
}
