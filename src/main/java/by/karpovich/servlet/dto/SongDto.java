package by.karpovich.servlet.dto;

import java.util.List;

public record SongDto(String name,
                      Long singerId,
                      Long albumId,
                      List<Long> authorsId) {
}
