package by.karpovich.servlet.servlets.singers;

import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/singers")
public class SingerServlet extends HttpServlet {

    private final SingerServiceImpl singerService = SingerServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Long.valueOf(req.getParameter("id"));
        if (id != null) {
            SingerDto dto = singerService.findById(id);
            if (dto != null) {
                String data = String.format("Name: %s", dto.surname());
                resp.getWriter().write(data);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        SingerDto dto = new SingerDto(name);
        SingerDto result = singerService.save(dto);

        if (result != null) {
            String data = String.format("Name: %s", result.surname());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long id = Long.valueOf(req.getParameter("id"));

        SingerDto result = new SingerDto(name);
        singerService.update(result, id);

        if (result != null) {
            String data = String.format("Name: %s", result.surname());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        if (id != null) {
            singerService.deleteById(id);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
