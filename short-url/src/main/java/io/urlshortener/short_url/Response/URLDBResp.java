package io.urlshortener.short_url.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URLDBResp {
    private String longURL;
    private String shortURL;
    public URLDBResp(String longURL, String shortURL) {
        this.longURL= longURL;
        this.shortURL= shortURL;
    }
}
