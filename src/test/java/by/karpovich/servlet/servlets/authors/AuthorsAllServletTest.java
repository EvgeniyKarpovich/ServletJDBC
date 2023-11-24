package by.karpovich.servlet.servlets.authors;

import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AuthorDto;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AuthorsAllServletTest {
    private static final String NAME = "Test Author";
    @Mock
    private AuthorServiceImpl authorService;
    @InjectMocks
    private AuthorsAllServlet authorServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();

        List<AuthorDto> authorDtoList = Arrays.asList(generateAuthorDto(), generateAuthorDto());
        String json = authorDtoList.stream().map(gson::toJson).collect(Collectors.joining());

        when(authorService.findAll()).thenReturn(authorDtoList);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        authorServlet.doGet(request, response);

        assertEquals(json, stringWriter.toString());
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto(NAME);
    }
}