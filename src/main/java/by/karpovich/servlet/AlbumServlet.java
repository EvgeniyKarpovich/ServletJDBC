package by.karpovich.servlet;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/albums")
public class AlbumServlet extends HttpServlet {

    private AlbumServiceImpl albumService = AlbumServiceImpl.getInstance();

    @Override
    public void  init() throws ServletException {
        System.out.printf("NA BLYAAAAAAAAA");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {

            Long id = Long.parseLong(idParam);
            AlbumDto album = albumService.findById(id);
            if (album != null) {
                String data = String.format("Name: %s, SingerId: %s", album.name(), album.singerId());
                resp.getWriter().write(data);
            }
        } else {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
