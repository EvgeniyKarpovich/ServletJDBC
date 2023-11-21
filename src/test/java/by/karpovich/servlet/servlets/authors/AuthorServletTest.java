package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.servlets.singers.SingerServlet;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AuthorServletTest {
    private static final String ID = "1";
    private static final String NAME = "Test Author";
    @Mock
    private AuthorServiceImpl authorService;
    @InjectMocks
    private AuthorServlet authorServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateAuthorDto());

        when(request.getParameter("id")).thenReturn(ID);
        when(authorService.findById(Long.valueOf(ID))).thenReturn(generateAuthorDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        authorServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals(json, stringWriter.toString());
    }

    @Test
    void doPost() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("name")).thenReturn(NAME);
        when(authorService.save(any(AuthorDto.class))).thenReturn(generateAuthorDto());
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(authorService).save(any(AuthorDto.class));
    }

    @Test
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("id")).thenReturn(ID);

        authorServlet.doPut(request, response);

        verify(authorService).update(any(AuthorDto.class), eq(Long.valueOf(ID)));
    }

    @Test
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(ID);

        authorServlet.doDelete(request, response);

        verify(authorService).deleteById(Long.valueOf(ID));
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto(NAME);
    }
}