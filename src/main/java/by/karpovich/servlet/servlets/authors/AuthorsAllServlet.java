package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/authors/all")
public class AuthorsAllServlet extends HttpServlet {

    private AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        List<AuthorDto> allDto = authorService.findAll();
        if (allDto != null) {
            for (AuthorDto dto : allDto) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                gson.toJson(dto, resp.getWriter());
            }
        }
    }
}
