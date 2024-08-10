package io.urlshortener.short_url.Controllers;

import io.urlshortener.short_url.Hashing.SHA256;
import io.urlshortener.short_url.Response.URLDBResp;
import io.urlshortener.short_url.DataAccessLayer.URL;
import io.urlshortener.short_url.DataAccessLayer.URLRepository;
import io.urlshortener.short_url.Requests.URLDBReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class URLController {

    @Autowired URLRepository urlRepository;

    @GetMapping(value= "/url")
    public List<URL> test() {
        List<URL> all= urlRepository.findAll();
        System.out.println(all);
        return all;
    }

    @PostMapping(value="/shorten-url")
    public URLDBResp addUrl(@RequestBody URLDBReq urlReq) {
        URL url=new URL(urlReq.getLongUrl());
        url.setShortUrl(url.getLongUrl());
        urlRepository.save(url);
        URLDBResp resp= new URLDBResp(url.getLongUrl(), url.getShortUrl());
        return resp;
    }
}
