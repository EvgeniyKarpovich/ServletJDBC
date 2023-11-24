package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.SingerEntity;
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

    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")

                    .withInitScript("db-migration.SQL");
    static String connectionUrl;
    private ConnectionManagerImpl connectionManagerImpl2 = Mockito.mock(ConnectionManagerImpl.class);
    @Mock
    private SingerResultSetMapperImpl singerResultSetMapper;
    @InjectMocks
    private SingerRepositoryImpl singerRepository = new SingerRepositoryImpl(connectionManagerImpl2);

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
                container.getPassword())).when(connectionManagerImpl2).getConnection();

        SingerEntity singer = new SingerEntity("TEST SINGER");
        singerRepository.save(singer);

        List<SingerEntity> all = singerRepository.findAll();

        assertEquals(1, all.size());
    }
}