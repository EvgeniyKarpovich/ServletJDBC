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
import java.util.List;

@WebServlet("/singers/all")
public class SingerAllServlet extends HttpServlet {

    private final SingerServiceImpl singerService;

    public SingerAllServlet(SingerServiceImpl singerService) {
        this.singerService = singerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        List<SingerDto> allDto = singerService.findAll();
        if (allDto != null) {
            for (SingerDto dto : allDto) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                gson.toJson(dto, resp.getWriter());
            }
        }
    }
}
