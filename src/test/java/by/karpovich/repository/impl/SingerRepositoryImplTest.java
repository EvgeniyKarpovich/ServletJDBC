package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    void save() {
        SingerEntity result = singerRepository.save(generateSingerEntity());

        assertNotNull(result.getId());
    }

    @Test
    void findAll() {
        List<SingerEntity> all = singerRepository.findAll();

        assertEquals(3, all.size());
    }

    @Test
    void findById() {
        SingerEntity actualResult = singerRepository.findById(1L).get();

        assertEquals(actualResult.getId(), 1L);
        assertEquals(actualResult.getSurname(), "Singer");
    }

    @Test
    void deleteById() {
        boolean actualResult = singerRepository.deleteById(2L);

        assertTrue(actualResult);
    }

    @Test
    void update() {
        String updateName = "update name";
        SingerEntity entity = singerRepository.findById(3L).get();
        entity.setSurname(updateName);
        singerRepository.update(entity);

        SingerEntity result = singerRepository.findById(entity.getId()).get();

        assertEquals(result.getSurname(), updateName);
    }

    private SingerEntity generateSingerEntity() {
        return new SingerEntity("Singer test");
    }
}