package by.karpovich.servlet.dto;

import java.util.List;

public record AuthorDtoOut(String name,
                           List<String> songsName) {
}
