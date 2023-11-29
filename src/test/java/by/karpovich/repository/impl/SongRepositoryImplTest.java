package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.model.AlbumEntity;
import by.karpovich.model.AuthorEntity;
import by.karpovich.model.SingerEntity;
import by.karpovich.model.SongEntity;
import by.karpovich.repository.mapper.impl.AlbumResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.AuthorResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SingerResultSetMapperImpl;
import by.karpovich.repository.mapper.impl.SongResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(authorResultSetMapper, songResultSetMapper, connectionManager);
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16")
                    .withInitScript("db-migration.SQL");

    static String connectionUrl;

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
    void findById() {
        SongEntity result = songRepository.findById(1L).get();

        assertEquals(result.getId(), 1L);
        assertEquals(result.getName(), "Song");
    }

    @Test
    void findByNameAndSingerId() {
        SongEntity result = songRepository.findByNameAndSingerId("Song", 1L).get();

        assertEquals(result.getId(), 1L);
        assertEquals(result.getName(), "Song");
    }

    @Test
    void findByAuthorId() {
        List<SongEntity> result = songRepository.findByAuthorId(1L);

        assertEquals(3, result.size());
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
        boolean result = songRepository.deleteById(2L);

        assertTrue(result);
    }

    @Test
    void save() {
        SongEntity result = songRepository.save(generateSongEntity());

        assertNotNull(result.getId());
    }

    @Test
    void update() {
        String updateName = "update name";

        SongEntity songEntity = songRepository.findById(3L).get();
        songEntity.setName(updateName);
        songRepository.update(songEntity);

        SongEntity result = songRepository.findById(songEntity.getId()).get();

        assertEquals(result.getName(), updateName);
    }

    private SongEntity generateSongEntity() {
        return new SongEntity("Author test", findSingerForSong(), findAlbumForSong(), findAuthorsForSong());
    }

    private AlbumEntity findAlbumForSong() {
        return albumRepository.findById(1L).get();
    }

    private SingerEntity findSingerForSong() {
        return singerRepository.findById(1L).get();
    }

    private List<AuthorEntity> findAuthorsForSong() {
        List<AuthorEntity> authors = new ArrayList<>();
        authors.add(authorRepository.findById(1L).get());
        authors.add(authorRepository.findById(2L).get());

        return authors;
    }
}
