package io.urlshortener.short_url.Controllers;

import io.urlshortener.short_url.Hashing.SHA256;
import io.urlshortener.short_url.Response.URLDBResp;
import io.urlshortener.short_url.DataAccessLayer.URL;
import io.urlshortener.short_url.DataAccessLayer.URLRepository;
import io.urlshortener.short_url.Requests.URLDBReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class URLController {

    @Autowired URLRepository urlRepository;

    @GetMapping(value= "/url")
    public List<URL> getAllUrls() {
        List<URL> all= urlRepository.findAll();
        return all;
    }

    @GetMapping(value= "/{shortUrl}")
    public RedirectView redirectToLongUrl(@PathVariable String shortUrl) {
        URL url= urlRepository.findByShortUrl("http://localhost:8080/"+shortUrl);
        RedirectView redirectView= new RedirectView();
        redirectView.setUrl(url==null ? "/404" : url.getLongUrl());
        return redirectView;
    }

    @PostMapping(value="/shorten-url")
    public URLDBResp addUrl(@RequestBody URLDBReq urlReq) {
        URL url = urlRepository.findByLongUrl(urlReq.getLongUrl()) != null
                ? urlRepository.findByLongUrl(urlReq.getLongUrl())
                : urlRepository.save(new URL(urlReq.getLongUrl()));
        URLDBResp resp= new URLDBResp(url.getLongUrl(), url.getShortUrl());
        return resp;
    }
}
