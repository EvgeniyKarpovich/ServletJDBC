package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

class AlbumRepositoryImplTest {
    private ConnectionManagerImpl connectionManager = Mockito.mock(ConnectionManagerImpl.class);
    private SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();
    private AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();
    private SingerRepositoryImpl singerRepository = new SingerRepositoryImpl(singerResultSetMapper, albumResultSetMapper, connectionManager);
    private AlbumRepositoryImpl albumRepository = new AlbumRepositoryImpl(albumResultSetMapper, singerResultSetMapper, connectionManager);

    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")
                    .withInitScript("db-migration.SQL");

    static String connectionUrl;

    @BeforeEach
    void setUp() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();
    }

    @BeforeAll
    static void beforeAll() {
        container.start();

        String[] url = container.getJdbcUrl().split("\\?");
        connectionUrl = url[0];
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void findById() {
        AlbumEntity result = albumRepository.findById(1L).get();

        assertEquals(result.getId(), 1L);
        assertEquals(result.getAlbumName(), "Album");
    }

    @Test
    void findByNameAndSingerId() {
        AlbumEntity result = albumRepository.findByNameAndSingerId("Album", 1L).get();

        assertEquals(result.getId(), 1L);
        assertEquals(result.getAlbumName(), "Album");
    }

    @Test
    void findAll() {
        List<AlbumEntity> result = albumRepository.findAll();

        assertEquals(3, result.size());
    }

    @Test
    void deleteById() {
        boolean result = albumRepository.deleteById(2L);

        assertTrue(result);
    }

    @Test
    void save() {
        AlbumEntity result = albumRepository.save(generateAlbumEntity());

        assertNotNull(result.getId());
    }

    @Test
    void update() {
        String updateName = "update name";

        AlbumEntity albumEntity = albumRepository.findById(3L).get();
        albumEntity.setAlbumName(updateName);
        albumRepository.update(albumEntity);

        AlbumEntity result = albumRepository.findById(albumEntity.getId()).get();

        assertEquals(result.getAlbumName(), updateName);
    }

    private AlbumEntity generateAlbumEntity() {

        return new AlbumEntity("Album test", findSingerForAlbum());
    }

    private SingerEntity findSingerForAlbum() {
        return singerRepository.findById(1L).get();
    }

}