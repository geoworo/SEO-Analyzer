package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;

import java.sql.SQLException;

public class UrlCheckController {
    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Id " + id + " not found"));
        String name = url.getName();
        try {
            HttpResponse<String> response = Unirest.get(name).asString();
            var status = response.getStatus();
            var doc = Jsoup.parse(response.getBody());
            var title = doc.title();
            var elementh1 = doc.selectFirst("h1");
            var h1 = (elementh1 == null ? null : elementh1.ownText());
            var elementdesc = doc.selectFirst("meta[name=description]");
            var desc = elementdesc == null ? null : elementdesc.attr("content");
            var check = new UrlCheck(status, title, h1, desc, id);
            UrlCheckRepository.save(check);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("type", "success");
            ctx.redirect(NamedRoutes.urlPath(id));
        } catch (UnirestException e) {
            ctx.redirect(NamedRoutes.urlPath(id));
        }
    }
}
