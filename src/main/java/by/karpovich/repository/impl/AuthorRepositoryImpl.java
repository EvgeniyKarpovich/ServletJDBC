package by.karpovich.repository.impl;

import by.karpovich.db.ConnectionManagerImpl;
import by.karpovich.exception.DaoException;
import by.karpovich.model.AuthorEntity;
import by.karpovich.repository.AuthorRepository;
import by.karpovich.repository.mapper.AuthorResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static final AuthorRepositoryImpl INSTANCE = new AuthorRepositoryImpl();
    private final AuthorResultSetMapperImpl resultSetMapper = new AuthorResultSetMapperImpl();

    private AuthorRepositoryImpl() {
    }

    public static AuthorRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private static final String SAVE_SQL = """
            INSERT INTO  authors (author_name)
            VALUES (?)
            """;

    private static final String DELETE_SQL = """
        
            """;

    private static final String UPDATE_SQL = """
         
            """;

    private static final String FIND_ALL_SQL = """
          
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
          
            """;

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            AuthorEntity authorEntity = null;

            if (resultSet.next()) {
                authorEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(authorEntity);
        } catch (SQLException e) {
            throw new DaoException("IN FIND BY ID");
        }
    }

    @Override
    public List<AuthorEntity> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        try (var connection = ConnectionManagerImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, authorEntity.getAuthorName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                authorEntity.setId(resultSet.getLong("id"));
            }
            return authorEntity;
        } catch (SQLException e) {
            throw new DaoException("IN SAVE");
        }
    }

    @Override
    public void update(AuthorEntity authorEntity) {

    }
}
