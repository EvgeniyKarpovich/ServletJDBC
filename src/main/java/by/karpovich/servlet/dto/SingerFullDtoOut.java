package by.karpovich.servlet.dto;

import java.util.List;

public record SingerFullDtoOut(Long id,
                               String surname,
                               List<String> albumsName) {
}
