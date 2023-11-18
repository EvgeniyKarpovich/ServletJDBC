package by.karpovich.servlet.dto;

import java.util.List;

public record SingerDtoOut(Long id,
                           String surname,
                           List<String> albumsName) {
}
