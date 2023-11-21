package by.karpovich.servlet.servlets.albums;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.service.impl.AuthorServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
import by.karpovich.servlet.dto.AuthorDto;
import by.karpovich.servlet.servlets.authors.AuthorServlet;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AlbumAllServletTest {

    private static final Long ID = 1L;
    private static final String NAME = "Test ALbum";
    @Mock
    private AlbumServiceImpl albumService;
    @InjectMocks
    private AlbumAllServlet albumAllServlet;


    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateAuthorDto());

        List<AlbumDto> result = Arrays.asList(generateAuthorDto());

        when(albumService.findAll()).thenReturn(result);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        albumAllServlet.doGet(request, response);

        assertEquals(json, stringWriter.toString());
    }

    private AlbumDto generateAuthorDto() {
        return new AlbumDto(NAME, ID);
    }
}