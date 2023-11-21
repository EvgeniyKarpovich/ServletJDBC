package by.karpovich.servlet.servlets.singers;

import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/singers")
public class SingerServlet extends HttpServlet {

    private SingerServiceImpl singerService = SingerServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        Long id = Long.valueOf(req.getParameter("id"));
        SingerDto dto = singerService.findById(id);
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

        SingerDto dto = new SingerDto(name);
        SingerDto result = singerService.save(dto);

        if (result != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(result, resp.getWriter());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long id = Long.valueOf(req.getParameter("id"));

        SingerDto result = new SingerDto(name);
        singerService.update(result, id);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        singerService.deleteById(id);
    }
}
