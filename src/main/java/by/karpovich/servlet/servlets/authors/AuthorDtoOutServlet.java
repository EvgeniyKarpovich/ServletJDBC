package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDtoOut;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authors/full")
public class AuthorDtoOutServlet extends HttpServlet {

    private final AuthorServiceImpl authorService;

    public AuthorDtoOutServlet(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Long id = Long.valueOf(req.getParameter("id"));

        if (id == null) {
            throw new ServletException("id must not be null");
        }

        AuthorDtoOut dto = authorService.findByIdFullDtoOut(id);
        if (dto != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(dto, resp.getWriter());
        }
    }
}
