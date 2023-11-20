package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/albums/all")
public class AuthorsAllServlet extends HttpServlet {

    private final AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AuthorDto> allDto = authorService.findAll();
        if (allDto != null) {
            for (AuthorDto dto : allDto) {
                String data = String.format("Name: %s\n", dto.name());
                resp.getWriter().write(data);
            }
        }
    }
}
