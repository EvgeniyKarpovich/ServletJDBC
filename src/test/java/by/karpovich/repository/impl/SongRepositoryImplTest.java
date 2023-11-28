package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

class SongRepositoryImplTest {
    private ConnectionManagerImpl connectionManager = Mockito.mock(ConnectionManagerImpl.class);
    private SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();
    private AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();
    private SongResultSetMapperImpl songResultSetMapper = new SongResultSetMapperImpl();
    private AuthorResultSetMapperImpl authorResultSetMapper = new AuthorResultSetMapperImpl();
    private SingerRepositoryImpl singerRepository = new SingerRepositoryImpl(singerResultSetMapper, albumResultSetMapper, connectionManager);
    private AlbumRepositoryImpl albumRepository = new AlbumRepositoryImpl(albumResultSetMapper, singerResultSetMapper, connectionManager);
    private SongRepositoryImpl songRepository = new SongRepositoryImpl(songResultSetMapper, authorResultSetMapper, albumResultSetMapper, singerResultSetMapper, connectionManager);
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
//        doAnswer(invocation -> DriverManager.getConnection(
//                container.getJdbcUrl(),
//                container.getUsername(),
//                container.getPassword())).when(connectionManager).getConnection();
//
//        AlbumEntity entity = new AlbumEntity("TEST Album");
//        SingerEntity singerEntity = new SingerEntity("TEST SINGER");
//        singerRepository.save(singerEntity);
//        entity.setSinger(singerEntity);
//        AlbumEntity saved = albumRepository.save(entity);
    }

    @Test
    void findByNameAndSingerId() {
    }

    @Test
    void findByAuthorId() {
    }

    @Test
    void findAll() {
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