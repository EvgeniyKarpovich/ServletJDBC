package by.karpovich.servlet.servlets.singers;

import by.karpovich.service.impl.SingerServiceImpl;
import by.karpovich.servlet.dto.SingerDto;
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
class SingerAllServletTest {
    private static final String ID = "1";
    private static final String NAME = "Test Singer";
    @Mock
    private SingerServiceImpl singerService;
    @InjectMocks
    private SingerAllServlet singerServlet;

    @Test
    void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Gson gson = new Gson();

        List<SingerDto> result = Arrays.asList(generateSingerDto(), generateSingerDto());
        String json = result.stream().map(gson::toJson).collect(Collectors.joining());

        when(singerService.findAll()).thenReturn(result);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        singerServlet.doGet(request, response);

        assertEquals(json, stringWriter.toString());
    }


    private SingerDto generateSingerDto() {
        return new SingerDto(NAME);
    }

}