package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    public static void save (Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        var createdAt = new Timestamp(System.currentTimeMillis());
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, createdAt);
            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                url.setId(keys.getLong(1));
                url.setCreatedAt(createdAt);
            } else {
                throw new SQLException("ID was not set");
            }
        }
    }

    public static List<Url> getUrls() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
             var statement = conn.prepareStatement(sql)) {
            var urls = statement.executeQuery();
            var result = new ArrayList<Url>();
            while (urls.next()) {
                var id = urls.getLong("id");
                var name = urls.getString("name");
                var createdAt = urls.getTimestamp("created_at");
                var url = new Url(id, name, createdAt);
                result.add(url);
            }
            return result;
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var url = getUrls().stream()
                .filter(u -> u.getId() == id)
                .findAny();
        return url;
    }
}
