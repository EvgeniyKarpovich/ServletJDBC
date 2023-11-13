package by.karpovich.servlet.dto;

import java.util.List;

public record SongDtoOut(Long id,
                         String name,
                         String singerName,
                         String albumName,
                         List<String> authorsName) {
}
