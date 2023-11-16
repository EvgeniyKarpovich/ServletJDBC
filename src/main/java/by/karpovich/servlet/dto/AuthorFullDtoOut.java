package by.karpovich.servlet.dto;

import java.util.List;

public record AuthorFullDtoOut(String name,
                               List<String> songsName) {
}
