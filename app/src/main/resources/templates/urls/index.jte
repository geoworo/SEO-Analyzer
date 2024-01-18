@import hexlet.code.App
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            @if(!page.getUrls().isEmpty())
                <tbody>
                @for(var url : page.getUrls().entrySet())
                    <tr>
                        <td>
                            ${url.getKey().getId()}
                        </td>
                        <td>
                            <a href="${NamedRoutes.urlPath(url.getKey().getId())}">
                                ${url.getKey().getName()}</a>
                        </td>
                        <td>
                            @if(url.getValue() != null)
                                ${App.getFormattedTime(url.getValue().getCreatedAt())}
                            @endif
                        </td>
                        <td>
                            @if(url.getValue() != null)
                                ${url.getValue().getStatusCode()}
                            @endif
                        </td>
                    </tr>
                @endfor
                </tbody>
            @endif
        </table>
    </div>

`
)