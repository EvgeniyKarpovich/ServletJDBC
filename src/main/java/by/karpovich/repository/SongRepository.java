package by.karpovich.repository;

import by.karpovich.model.SongEntity;

import java.util.Optional;

public interface SongRepository extends BaseRepository<SongEntity, Long> {

    Optional<SongEntity> findByName(String name);
}
