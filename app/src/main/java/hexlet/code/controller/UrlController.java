package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

public class UrlController {

    public static void showUrls(Context ctx) throws SQLException {
        var urls = UrlRepository.getUrls();
        var checks = new HashMap<Long, UrlCheck>();
        for (var url : urls) {
            UrlCheckRepository.findLastCheck(url.getId()).ifPresentOrElse(
                    (o) -> checks.put(url.getId(), o),
                    () -> checks.put(url.getId(), null)
            );
        }
        var page = new UrlsPage(urls, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setType(ctx.consumeSessionAttribute("type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var checks = UrlCheckRepository.getChecks(id);
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id " + id + " not found"));
        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setType(ctx.consumeSessionAttribute("type"));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var param = ctx.formParamAsClass("url", String.class).getOrDefault(null);
        URL addedUrl = null;

        try {
            addedUrl = new URL(param);
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("type", "danger");
            ctx.redirect((NamedRoutes.rootPath()));
        }

        if (addedUrl != null) {
            String protocol = addedUrl.getProtocol();
            String authority = addedUrl.getAuthority();
            String host = String.format("%s://%s", protocol, authority);
            var url = new Url(host);

            if (UrlRepository.getUrls().stream().noneMatch(o -> o.getName().equals(host))) {
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("type", "success");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("type", "info");
                ctx.redirect(NamedRoutes.urlsPath());
            }
        }
    }
}
