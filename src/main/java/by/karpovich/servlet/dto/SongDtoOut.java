package by.karpovich.servlet.dto;

import java.util.List;

public record SongDtoOut(Long id,
                         String name,
                         Long singerId,
                         String singerName,
                         Long albumId,
                         String albumName,
                         List<String> authorsName) {
}
