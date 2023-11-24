package by.karpovich.servlet.servlets.albums;

import by.karpovich.service.impl.AlbumServiceImpl;
import by.karpovich.servlet.dto.AlbumDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AlbumServletTest {
    private static final String ID = "1";
    private static final String NAME = "Test Album";
    @Mock
    private AlbumServiceImpl albumService;
    @InjectMocks
    private AlbumServlet albumServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateAlbumDto());

        when(request.getParameter("id")).thenReturn(ID);
        when(albumService.findById(Long.valueOf(ID))).thenReturn(generateAlbumDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        albumServlet.doGet(request, response);

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

        Gson gson = new Gson();
        String json = gson.toJson(generateAlbumDto());

        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("singerId")).thenReturn(ID);
        when(albumService.save(any(AlbumDto.class))).thenReturn(generateAlbumDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        albumServlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals(json, stringWriter.toString());
    }

    @Test
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("singerId")).thenReturn(ID);

        albumServlet.doPut(request, response);

        verify(albumService).update(any(AlbumDto.class), eq(Long.valueOf(ID)));
    }

    @Test
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(ID);

        albumServlet.doDelete(request, response);

        verify(albumService).deleteById(Long.valueOf(ID));
    }

    private AlbumDto generateAlbumDto() {
        return new AlbumDto(NAME, 1L);
    }
}