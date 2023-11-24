package by.karpovich.servlet.servlets.songs;

import by.karpovich.service.impl.SongServiceImpl;
import by.karpovich.servlet.dto.SongDto;
import by.karpovich.servlet.dto.SongDtoOut;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SongsOutServletTest {
    private static final String ID = "1";
    private static final String NAME = "Test Song";
    private static final List<Long> AUTHORS = Arrays.asList(1L, 2L);
    @Mock
    private SongServiceImpl songService;
    @InjectMocks
    private SongsOutServlet songServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();
        String json = gson.toJson(generateSongDtoOut());

        when(request.getParameter("id")).thenReturn(ID);
        when(songService.findByIdFullDtoOut(anyLong())).thenReturn(generateSongDtoOut());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        songServlet.doGet(request, response);

        assertEquals(json, stringWriter.toString());
    }

    private SongDto generateSongDto() {
        return new SongDto(NAME,
                1L,
                1L,
                AUTHORS);
    }

    private SongDtoOut generateSongDtoOut() {
        List<String> authorsId = Arrays.asList("One Author", "Two Author");
        return new SongDtoOut(1L, NAME, 1L, "Test Song", 1L, "Album test", authorsId);
    }

}