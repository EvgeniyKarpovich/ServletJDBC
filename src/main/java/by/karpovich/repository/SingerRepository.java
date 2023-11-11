package by.karpovich.repository;

import by.karpovich.model.SingerEntity;

import java.util.Optional;

public interface SingerRepository extends BaseRepository<SingerEntity, Long> {

    Optional<SingerEntity> findByName(String name);
}
