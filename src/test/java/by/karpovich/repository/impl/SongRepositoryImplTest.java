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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

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
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST3 Album");
        SingerEntity singerEntity = new SingerEntity("TEST3 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST5 AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST6 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST3 Song", singerEntity, albumEntity, authorEntities);

        SongEntity savedSong = songRepository.save(songEntity);
        SongEntity result = songRepository.findById(savedSong.getId()).get();

        assertEquals(result.getId(), savedSong.getId());
        assertEquals(result.getName(), savedSong.getName());
    }

    @Test
    void findByNameAndSingerId() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST2 Album");
        SingerEntity singerEntity = new SingerEntity("TEST2 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST3 AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST4 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST2 Song", singerEntity, albumEntity, authorEntities);

        SongEntity savedSong = songRepository.save(songEntity);
        SongEntity result = songRepository.findByNameAndSingerId(songEntity.getName(), singerEntity.getId()).get();

        assertEquals(result.getId(), savedSong.getId());
        assertEquals(result.getName(), savedSong.getName());
    }

    @Test
    void findByAuthorId() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST4 Album");
        SingerEntity singerEntity = new SingerEntity("TEST4 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST7 AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST8 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST4 Song", singerEntity, albumEntity, authorEntities);

        SongEntity savedSong = songRepository.save(songEntity);
        List<SongEntity> result = songRepository.findByAuthorId(authorEntity.getId());

        assertEquals(1, result.size());
    }

    @Test
    void findAll() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST5 Album");
        SingerEntity singerEntity = new SingerEntity("TEST5 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST9 AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST10 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST5 Song", singerEntity, albumEntity, authorEntities);
        SongEntity songEntity2 = new SongEntity("TEST6 Song", singerEntity, albumEntity, authorEntities);

        songRepository.save(songEntity);
        songRepository.save(songEntity2);

        List<SongEntity> result = songRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST6 Album");
        SingerEntity singerEntity = new SingerEntity("TEST6 SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST11 AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST22 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST6 Song", singerEntity, albumEntity, authorEntities);

        SongEntity savedSong = songRepository.save(songEntity);

        boolean result = songRepository.deleteById(savedSong.getId());

        assertTrue(result);
    }

    @Test
    void save() {
        doAnswer(invocation -> DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())).when(connectionManager).getConnection();

        AlbumEntity albumEntity = new AlbumEntity("TEST Album");
        SingerEntity singerEntity = new SingerEntity("TEST SINGER");
        singerRepository.save(singerEntity);
        albumEntity.setSinger(singerEntity);
        albumRepository.save(albumEntity);
        AuthorEntity authorEntity = new AuthorEntity("TEST AUTHOR");
        AuthorEntity authorEntity2 = new AuthorEntity("TEST2 AUTHOR");
        List<AuthorEntity> authorEntities = new ArrayList<>();
        authorEntities.add(authorEntity);
        authorEntities.add(authorEntity2);
        authorRepository.save(authorEntity);
        authorRepository.save(authorEntity2);

        SongEntity songEntity = new SongEntity("TEST Song", singerEntity, albumEntity, authorEntities);

        SongEntity result = songRepository.save(songEntity);

        assertNotNull(result.getId());
    }

    @Test
    void update() {
    }
}