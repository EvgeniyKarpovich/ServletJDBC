package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        SingerEntity singer = new SingerEntity("TEST SINGER");
        singerRepository.save(singer);

        List<SingerEntity> all = singerRepository.findAll();

        assertEquals(1, all.size());
    }
}