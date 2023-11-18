package by.karpovich.repository.impl;

import by.karpovich.model.SingerEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingerRepositoryImplTest {

    private SingerRepositoryImpl singerRepository = SingerRepositoryImpl.getInstance();
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withInitScript("db-migration.SQL")
                    .withPassword("postgres");

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }


    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findAll() {
        SingerEntity singer = new SingerEntity("TEST SINGER");
        singerRepository.save(singer);

        List<SingerEntity> all = singerRepository.findAll();

        assertEquals(1, all.size());
    }

    @Test
    void deleteById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}