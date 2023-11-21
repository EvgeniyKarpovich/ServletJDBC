package by.karpovich.servlet.servlets.singers;

import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
import by.karpovich.servlet.servlets.songs.SongsOutServlet;
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
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SingerServletTest {

    private static final String ID = "1";
    private static final String NAME = "Test Singer";
    @Mock
    private SingerServiceImpl singerService;
    @InjectMocks
    private SingerServlet singerServlet;

    @Test
    void doGet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateSingerDto());

        when(request.getParameter("id")).thenReturn(ID);
        when(singerService.findById(Long.valueOf(ID))).thenReturn(generateSingerDto());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        singerServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals(json, stringWriter.toString());
    }

    @Test
    void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        SingerDto singerDto = new SingerDto(NAME);
        String json = gson.toJson(singerDto);

        when(request.getParameter("name")).thenReturn(NAME);
        when(singerService.save(any(SingerDto.class))).thenReturn(singerDto);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        singerServlet.doPost(request, response);

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

        singerServlet.doPut(request, response);

        verify(singerService).update(any(SingerDto.class), eq(Long.valueOf(ID)));
    }

    @Test
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(ID);

        singerServlet.doDelete(request, response);

        verify(singerService).deleteById(Long.valueOf(ID));
    }

    private SingerDto generateSingerDto() {
        return new SingerDto(NAME);
    }
}