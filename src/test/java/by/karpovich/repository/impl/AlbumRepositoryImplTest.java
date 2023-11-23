package by.karpovich.repository.impl;

import by.karpovich.dase.ALRepo;
import by.karpovich.dase.ConnectionManager;
import by.karpovich.dase.ConnectionManagerImpl2;
import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;



class AlbumRepositoryImplTest {

    ConnectionManagerImpl2 connectionManager = Mockito.mock(ConnectionManagerImpl2.class);
    SingerResultSetMapperImpl singerResultSetMapper = new SingerResultSetMapperImpl();
    AlbumResultSetMapperImpl albumResultSetMapper = new AlbumResultSetMapperImpl();
    @InjectMocks
    private ALRepo albumRepository = new ALRepo(albumResultSetMapper, singerResultSetMapper, connectionManager);
    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")
                    .withInitScript("db-migration.SQL");

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }
    @Test
    void findAll() throws SQLException {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();


        SingerEntity singerEntity = new SingerEntity(1L, "TEST2 SINGER");
        albumRepository.save(new AlbumEntity("ALBUM ONE1", singerEntity));

        List<AlbumEntity> result = albumRepository.findAll();
        assertEquals(1, result.size());
    }
}