package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

class SingerRepositoryImplTest {
    private ConnectionManagerImpl connectionManager = Mockito.mock(ConnectionManagerImpl.class);

    private SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();

    private AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();
    @InjectMocks
    private SingerRepositoryImpl singerRepository = new SingerRepositoryImpl(singerResultSetMapper, albumResultSetMapper, connectionManager);

    static String connectionUrl;

    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")

                    .withInitScript("db-migration.SQL");

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
    void save() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        SingerEntity entity = new SingerEntity("TEST Singer");
        SingerEntity result = singerRepository.save(entity);

        assertNotNull(result.getId());
    }

    @Test
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        SingerEntity singer = new SingerEntity("TEST2 SINGER");
        singerRepository.save(singer);

        List<SingerEntity> all = singerRepository.findAll();

        assertEquals(1, all.size());
    }

    @Test
    void findById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        SingerEntity entity = new SingerEntity("TEST3 SINGER");
        SingerEntity saved = singerRepository.save(entity);

        SingerEntity actualResult = singerRepository.findById(saved.getId()).get();

        assertEquals(actualResult.getId(), saved.getId());
        assertEquals(actualResult.getSurname(), entity.getSurname());
    }

    @Test
    void deleteById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        SingerEntity entity = new SingerEntity("TEST4 SINGER");
        SingerEntity saved = singerRepository.save(entity);

        boolean actualResult = singerRepository.deleteById(saved.getId());

        assertTrue(actualResult);
    }

    @Test
    void update() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        String updateName = "update name";

        SingerEntity entity = new SingerEntity("TEST5 SINGER");
        singerRepository.save(entity);

        entity.setSurname("update name");

        singerRepository.update(entity);
        SingerEntity result = singerRepository.findById(entity.getId()).get();

        assertEquals(result.getSurname(), updateName);
    }
}