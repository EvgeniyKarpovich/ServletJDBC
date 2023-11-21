package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDtoOut;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AuthorDtoOutServletTest {
    private static final String ID = "1";
    private static final String NAME = "Test Author";
    @Mock
    private AuthorServiceImpl authorService;
    @InjectMocks
    private AuthorDtoOutServlet authorServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateAuthorDtoOut());

        when(request.getParameter("id")).thenReturn(ID);
        when(authorService.findByIdFullDtoOut(Long.valueOf(ID))).thenReturn(generateAuthorDtoOut());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        authorServlet.doGet(request, response);

        assertEquals(json, stringWriter.toString());
    }

    private AuthorDtoOut generateAuthorDtoOut() {
        List<String> songsName = Arrays.asList("Songs1", "Songs1");
        return new AuthorDtoOut(NAME, songsName);
    }
}