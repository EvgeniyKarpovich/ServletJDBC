package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity entity = new AlbumEntity("TEST Album");
        SingerEntity singerEntity = new SingerEntity("TEST SINGER");
        singerRepository.save(singerEntity);
        entity.setSinger(singerEntity);

        AlbumEntity saved = albumRepository.save(entity);

        AlbumEntity actualResult = albumRepository.findById(saved.getId()).get();

        assertEquals(actualResult.getId(), saved.getId());
        assertEquals(actualResult.getAlbumName(), entity.getAlbumName());
    }

    @Test
    void findByNameAndSingerId() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity entity = new AlbumEntity("TEST2 Album");
        SingerEntity singerEntity = new SingerEntity("TEST2 SINGER");
        SingerEntity savedSinger = singerRepository.save(singerEntity);
        entity.setSinger(singerEntity);
        AlbumEntity savedAlbum = albumRepository.save(entity);

        AlbumEntity result = albumRepository.findByNameAndSingerId(entity.getAlbumName(), savedSinger.getId()).get();

        assertEquals(result.getId(), savedAlbum.getId());
        assertEquals(result.getAlbumName(), savedAlbum.getAlbumName());
    }

    @Test
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST3 Album");
        SingerEntity singerEntity = new SingerEntity("TEST3 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);

        AlbumEntity albumEntity2 = new AlbumEntity("TEST4 Album");
        SingerEntity singerEntity2 = new SingerEntity("TEST4 SINGER");
        singerRepository.save(singerEntity2);
        albumEntity2.setSinger(singerEntity2);
        albumRepository.save(albumEntity2);

        List<AlbumEntity> result = albumRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity entity = new AlbumEntity("TEST5 Album");
        SingerEntity singerEntity = new SingerEntity("TEST5 SINGER");
        singerRepository.save(singerEntity);
        entity.setSinger(singerEntity);
        AlbumEntity savedAlbum = albumRepository.save(entity);

        boolean result = albumRepository.deleteById(savedAlbum.getId());

        assertTrue(result);
    }

    @Test
    void save() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity entity = new AlbumEntity("TEST6 Album");
        SingerEntity singerEntity = new SingerEntity("TEST6 SINGER");
        singerRepository.save(singerEntity);
        entity.setSinger(singerEntity);

        AlbumEntity result = albumRepository.save(entity);

        assertNotNull(result.getId());
    }

    @Test
    void update() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        String updateName = "update name";

        AlbumEntity albumEntity = new AlbumEntity("TEST7 Album");
        SingerEntity singerEntity = new SingerEntity("TEST7 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        albumEntity.setAlbumName(updateName);

        albumRepository.update(albumEntity);

        AlbumEntity result = albumRepository.findById(albumEntity.getId()).get();

        assertEquals(result.getAlbumName(), updateName);
    }
}