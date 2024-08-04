package io.urlshortener.short_url.Requests;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class URLDBReq {

    private int urlId;
    private String shortUrl;
    private String longUrl;

}
