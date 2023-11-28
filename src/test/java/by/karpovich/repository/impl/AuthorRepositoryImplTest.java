package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AuthorRepositoryImplTest {

    private ConnectionManagerImpl connectionManager = Mockito.mock(ConnectionManagerImpl.class);

    private AuthorResultSetMapperImpl authorResultSetMapper = new AuthorResultSetMapperImpl();

    private SongResultSetMapperImpl songResultSetMapper = new SongResultSetMapperImpl();
    @InjectMocks
    private AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(authorResultSetMapper, songResultSetMapper, connectionManager);
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

        AuthorEntity entity = new AuthorEntity("TEST Author");
        AuthorEntity result = authorRepository.save(entity);

        assertNotNull(result.getId());
    }

    @Test
    void findById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AuthorEntity entity = new AuthorEntity("TEST2 Author");

        AuthorEntity saved = authorRepository.save(entity);

        AuthorEntity result = authorRepository.findById(saved.getId()).get();

        assertEquals(result.getId(), saved.getId());
        assertEquals(result.getAuthorName(), entity.getAuthorName());
    }

    @Test
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AuthorEntity author = new AuthorEntity("TEST3 Author");
        AuthorEntity author2 = new AuthorEntity("TEST4 Author");
        authorRepository.save(author);
        authorRepository.save(author2);

        List<AuthorEntity> result = authorRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AuthorEntity author = new AuthorEntity("TEST5 Author");
        AuthorEntity save = authorRepository.save(author);

        boolean result = authorRepository.deleteById(save.getId());

        assertTrue(result);
    }

    @Test
    void update() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        String updateName = "update name";

        AuthorEntity entity = new AuthorEntity("TEST6 Author");
        authorRepository.save(entity);

        entity.setAuthorName(updateName);
        authorRepository.update(entity);

        AuthorEntity result = authorRepository.findById(entity.getId()).get();

        assertEquals(result.getAuthorName(), updateName);
    }
}