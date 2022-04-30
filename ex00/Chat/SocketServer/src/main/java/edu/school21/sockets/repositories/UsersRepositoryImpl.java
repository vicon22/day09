package edu.school21.sockets.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import static java.lang.String.format;


@Component
public class UsersRepositoryImpl implements UsersRepository {

    HikariDataSource dataSource;
    private final String tableName = "users";
    private final JdbcTemplate jdbcTemplate;

    RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("password"));
    };

    @Autowired
    public UsersRepositoryImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.queryForObject(format("SELECT * FROM %s WHERE id = %d", tableName, id), ROW_MAPPER);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(format("SELECT * FROM %s", tableName), ROW_MAPPER);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(format("INSERT INTO %s (name, password) VALUES ('%s', '%s')", tableName, entity.getName(), entity.getPassword()));
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(format("UPDATE %s  SET name = %s, password = %s WHERE id = %d", tableName, entity.getName(), entity.getPassword(), entity.getIdentifier()));
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(format("DELETE FROM %s WHERE id = %d", tableName, id));
    }

    @Override
    public Optional<User> findByName(String name) {

        User user = null;
        List<User> userList = jdbcTemplate.query(format("SELECT * FROM %s WHERE name = '%s'", tableName, name), ROW_MAPPER);
        if (userList.size() != 0){
            user = userList.get(0);
        }
        return Optional.ofNullable(user);
    }
}
