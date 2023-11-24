package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class AuthorRepositoryImplTest {

    @Mock
    private ConnectionManagerImpl connectionManager;
    @Mock
    private AuthorResultSetMapperImpl authorResultSetMapper;
    @Mock
    private SongResultSetMapperImpl songResultSetMapper;
    @InjectMocks
    private AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(connectionManager);
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
    void findById() {
//        doAnswer(invocation -> DriverManager.getConnection(
//                container.getJdbcUrl(),
//                container.getUsername(),
//                container.getPassword())).when(connectionManager).getConnection();
//
//        AuthorEntity entity = authorRepository.save(new AuthorEntity("TEST Author"));
//
//        Optional<AuthorEntity> actualResult = authorRepository.findById(entity.getId());
//
//        assertThat(actualResult).isPresent();
//        assertThat(actualResult.get()).isEqualTo(entity);
    }

    @Test
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AuthorEntity author = new AuthorEntity("TEST Author");
        AuthorEntity author2 = new AuthorEntity("TEST2 Author");
        authorRepository.save(author);
        authorRepository.save(author2);

        List<AuthorEntity> all = authorRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void deleteById() {
//        doAnswer(invocation -> DriverManager.getConnection(
//                container.getJdbcUrl(),
//                container.getUsername(),
//                container.getPassword())).when(connectionManager).getConnection();
//
//        AuthorEntity author = new AuthorEntity("TEST Author");
//        AuthorEntity save = authorRepository.save(author);
//
//        boolean actualResult = authorRepository.deleteById(save.getId());
//
//        assertTrue(actualResult);
    }

    @Test
    void save() {
//        doAnswer(invocation -> DriverManager.getConnection(
//                container.getJdbcUrl(),
//                container.getUsername(),
//                container.getPassword())).when(connectionManager).getConnection();
//
//        AuthorEntity entity = new AuthorEntity("TEST Author");
//        AuthorEntity result = authorRepository.save(entity);
//
//        assertNotNull(result.getId());
    }

//    @Test
//    void update() {
//        doAnswer(invocation -> DriverManager.getConnection(
//                container.getJdbcUrl(),
//                container.getUsername(),
//                container.getPassword())).when(connectionManager).getConnection();
//
//        AuthorEntity entity = new AuthorEntity("TEST Author");
//        AuthorEntity save = authorRepository.save(entity);
//
//        entity.setAuthorName("update name");
//         authorRepository.update(entity);
//
//        AuthorEntity result = authorRepository.findById(save.getId()).get();
//        assertThat(result).isEqualTo(entity);
//    }
}