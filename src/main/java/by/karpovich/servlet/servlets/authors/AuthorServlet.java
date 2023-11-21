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

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {

    private AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        Long id = Long.valueOf(req.getParameter("id"));
        AuthorDto dto = authorService.findById(id);
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

        AuthorDto dto = new AuthorDto(name);
        AuthorDto result = authorService.save(dto);

        if (result != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(dto, resp.getWriter());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();

        String name = req.getParameter("name");
        Long id = Long.valueOf(req.getParameter("id"));

        AuthorDto result = new AuthorDto(name);
        authorService.update(result, id);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        authorService.deleteById(id);
    }
}
