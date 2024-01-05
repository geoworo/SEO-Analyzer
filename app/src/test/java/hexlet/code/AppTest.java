package hexlet.code;

import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.repository.UrlRepository.getUrls;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private static Javalin app;
    private static MockWebServer mockWebServer;
    private final String url1 = "https://example.com";
    private final String url2 = "https://en.wikipedia.org/wiki/Main_Page";
    private final String nonexistentUrl = "https://websiteee52.com";

    @BeforeAll
    public static void setUpServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @AfterAll
    public static void pullDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("Анализатор страниц"));
        }));
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + url1;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("https://example.com"));
            assertThat(getUrls().size()).isEqualTo(1);
            assertThat(getUrls().get(0).getName()).isEqualTo("https://example.com");
        }));
    }

    @Test
    public void testCreateIncorrectUrl() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + nonexistentUrl;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(getUrls().size()).isEqualTo(0);
        }));
    }
}
