package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.rendering.template.JavalinJte;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class App {
    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String readResourceFile(String filename) throws IOException {
        var path = Paths.get("src", "main", "resources", filename);
        return Files.readString(path);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("/templates/", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static void main(String[] Args) throws SQLException, IOException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        JavalinJte.init(createTemplateEngine());
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;"));

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        try (var connection = dataSource.getConnection();
                var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        app.post(NamedRoutes.rootPath(), UrlController::create);

        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.get(NamedRoutes.urlsPath(), UrlController::index);


        return app;
    }
}
