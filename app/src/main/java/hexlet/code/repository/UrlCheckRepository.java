package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck check) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, h1, title, " +
                "description, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        var createdAt = new Timestamp(System.currentTimeMillis());
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, check.getUrlId());
            stmt.setInt(2, check.getStatusCode());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getTitle());
            stmt.setString(5, check.getDescription());
            stmt.setTimestamp(6, check.getCreatedAt());
            stmt.executeUpdate();
            var keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                check.setId(keys.getLong(1));
                check.setCreatedAt(createdAt);
            } else {
                throw new SQLException("ID was not set");
            }
        }
    }

    public static List<UrlCheck> getChecks(Long urlId) throws SQLException {
        var sql= "SELECT * FROM url_checks WHERE url_id = ?";
        try (var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var checks = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (checks.next()) {
                var id = checks.getLong("id");
                var statusCode = checks.getInt("status_code");
                var title = checks.getString("title");
                var h1 = checks.getString("h1");
                var description = checks.getString("description");
                var createdAt = checks.getTimestamp("created_at");
                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                check.setId(id);
                check.setCreatedAt(createdAt);
                result.add(check);
            }
            return result;
        }
    }

    public static Optional<UrlCheck> find(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE urlId = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var createdAt = resultSet.getTimestamp("created_at");
                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                check.setId(id);
                check.setCreatedAt(createdAt);
                return Optional.of(check);
            }
            return Optional.empty();
        }
    }
}
