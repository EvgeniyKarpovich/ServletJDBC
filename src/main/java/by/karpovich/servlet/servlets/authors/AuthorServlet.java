package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {

    private final AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        AuthorDto dto = authorService.findById(id);
        if (dto != null) {
            String data = String.format("Name: %s", dto.name());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        AuthorDto dto = new AuthorDto(name);
        AuthorDto result = authorService.save(dto);

        if (result != null) {
            String data = String.format("Name: %s", result.name());
            resp.getWriter().write(data);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Long id = Long.valueOf(req.getParameter("id"));

        AuthorDto result = new AuthorDto(name);
        authorService.update(result, id);

        String data = String.format("Name: %s", result.name());
        resp.getWriter().write(data);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        authorService.deleteById(id);
    }
}
