package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void showRoot(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setType(ctx.consumeSessionAttribute("type"));
        ctx.render("showUrls.jte", Collections.singletonMap("page", page));
    }
}
