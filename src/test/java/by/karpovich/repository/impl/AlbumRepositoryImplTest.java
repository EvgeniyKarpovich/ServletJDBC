package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class AlbumRepositoryImplTest {
//
//    private final AlbumRepositoryImpl albumRepository = new AlbumRepositoryImpl();
//    @Container
//    public static final PostgreSQLContainer<?> container =
//            new PostgreSQLContainer<>("postgres:16")
//                    .withDatabaseName("postgres")
//                    .withUsername("postgres")
//                    .withInitScript("db-migration.SQL")
//                    .withPassword("postgres");
//
//    @BeforeAll
//    static void beforeAll() {
//        container.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        container.stop();
//    }
//
//
//    @Test
//    void findById() {
//
//    }
//
//    @Test
//    void findByNameAndSingerId() {
//    }
//
//    @Test
//    void findAll() {
//        SingerEntity singerEntity = new SingerEntity(1L, "TEST2 SINGER");
//        albumRepository.save(new AlbumEntity("TEST13333fr9gf3j67333 ALBUM ONE", singerEntity));
////        albumRepository.save(new AlbumEntity("TEST ALBUM TWO"));
//
//        List<AlbumEntity> result = albumRepository.findAll();
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void deleteById() {
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void update() {
//    }
}