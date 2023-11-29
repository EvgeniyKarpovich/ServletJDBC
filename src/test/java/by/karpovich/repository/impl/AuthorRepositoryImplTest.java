package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
        AuthorEntity result = authorRepository.save(generateAuthorEntity());

        assertNotNull(result.getId());
    }

    @Test
    void findById() {
        AuthorEntity result = authorRepository.findById(1L).get();

        assertEquals(result.getId(), 1L);
        assertEquals(result.getAuthorName(), "Author");
    }

    @Test
    void findAll() {
        List<AuthorEntity> result = authorRepository.findAll();

        assertEquals(3, result.size());
    }

    @Test
    void deleteById() {
        boolean result = authorRepository.deleteById(2L);

        assertTrue(result);
    }

    @Test
    void update() {
        String updateName = "update name";
        AuthorEntity entity = authorRepository.findById(3L).get();
       entity.setAuthorName(updateName);

        authorRepository.update(entity);

        AuthorEntity result = authorRepository.findById(entity.getId()).get();

        assertEquals(result.getAuthorName(), updateName);
    }

    private AuthorEntity generateAuthorEntity() {
        return new AuthorEntity("Author test");
    }

}