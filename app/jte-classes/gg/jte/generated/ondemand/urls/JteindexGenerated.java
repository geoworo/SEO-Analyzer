package gg.jte.generated.ondemand.urls;
import hexlet.code.App;
import hexlet.code.util.NamedRoutes;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repository.UrlRepository;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,4,4,6,6,9,9,21,21,23,23,26,26,26,29,29,29,29,29,29,29,29,29,30,30,30,33,33,34,34,34,35,35,38,38,39,39,39,40,40,43,43,45,45,49,49,49};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"container-lg mt-5\">\n        <h1>Сайты</h1>\n        <table class=\"table table-bordered table-hover mt-3\">\n            <thead>\n            <tr>\n                <th class=\"col-1\">ID</th>\n                <th>Имя</th>\n                <th class=\"col-2\">Последняя проверка</th>\n                <th class=\"col-1\">Код ответа</th>\n            </tr>\n            </thead>\n            ");
				if (!page.getUrls().isEmpty()) {
					jteOutput.writeContent("\n                <tbody>\n                ");
					for (var url : page.getUrls()) {
						jteOutput.writeContent("\n                    <tr>\n                        <td>\n                            ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getId());
						jteOutput.writeContent("\n                        </td>\n                        <td>\n                            <a");
						var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
						if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
							jteOutput.writeContent(" href=\"");
							jteOutput.setContext("a", "href");
							jteOutput.writeUserContent(__jte_html_attribute_0);
							jteOutput.setContext("a", null);
							jteOutput.writeContent("\"");
						}
						jteOutput.writeContent(">\n                                ");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(url.getName());
						jteOutput.writeContent("</a>\n                        </td>\n                        <td>\n                            ");
						if (page.getChecks().get(url.getId()) != null) {
							jteOutput.writeContent("\n                                ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(App.getFormattedTime(page.getChecks().get(url.getId()).getCreatedAt()));
							jteOutput.writeContent("\n                            ");
						}
						jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
						if (page.getChecks().get(url.getId()) != null) {
							jteOutput.writeContent("\n                                ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(page.getChecks().get(url.getId()).getStatusCode());
							jteOutput.writeContent("\n                            ");
						}
						jteOutput.writeContent("\n                        </td>\n                    </tr>\n                ");
					}
					jteOutput.writeContent("\n                </tbody>\n            ");
				}
				jteOutput.writeContent("\n        </table>\n    </div>\n\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
