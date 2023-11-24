package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SongServletTest {

    private static final String ID = "1";
    private static final String NAME = "Test Song";
    private static final List<Long> AUTHORS = Arrays.asList(1L, 2L);
    @Mock
    private SongServiceImpl songService;
    @InjectMocks
    private SongServlet songServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateSongDto());

        when(request.getParameter("id")).thenReturn(ID);
        when(songService.findById(Long.valueOf(ID))).thenReturn(generateSongDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        songServlet.doGet(request, response);

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

        String json = gson.toJson(generateSongDto());

        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("singerId")).thenReturn(ID);
        when(request.getParameter("albumId")).thenReturn(ID);
        when(request.getParameter("authorsId")).thenReturn("1,2");
        when(songService.save(any(SongDto.class))).thenReturn(generateSongDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        songServlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals(json, stringWriter.toString());
    }

    @Test
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("singerId")).thenReturn(ID);
        when(request.getParameter("albumId")).thenReturn(ID);
        when(request.getParameter("authorsId")).thenReturn("1,2");
        when(request.getParameter("id")).thenReturn(ID);

        songServlet.doPut(request, response);

        verify(songService).update(any(SongDto.class), eq(Long.valueOf(ID)));
    }

    @Test
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(ID);

        songServlet.doDelete(request, response);

        verify(songService).deleteById(Long.valueOf(ID));
    }

    private SongDto generateSongDto() {
        return new SongDto(NAME,
                1L,
                1L,
                AUTHORS);
    }
}