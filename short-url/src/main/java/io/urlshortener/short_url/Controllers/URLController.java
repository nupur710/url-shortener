package io.urlshortener.short_url.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLController {

    @PostMapping(value="/shorten-url")
    public void addUrl() {

    }
}
