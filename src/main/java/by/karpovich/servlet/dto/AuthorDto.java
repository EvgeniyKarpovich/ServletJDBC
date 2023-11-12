package by.karpovich.servlet.dto;

import java.util.List;

public record AuthorDto(String name,
                        List<Long> songsId) {
}
