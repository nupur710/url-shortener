package io.urlshortener.short_url.DataAccessLayer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class URL {

    public URL(String longUrl) {
        this.longUrl= longUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;
    @Getter
    private String shortUrl;
    @Getter
    @Setter
    private String longUrl;

    public String setShortUrl(String longUrl) {
        this.shortUrl= longUrl.split("/")[0];
        return shortUrl;
    }

    public String toString() {
        return "id: " + this.id + "\n long url: " + this.longUrl + "\n short url: " + this.shortUrl;
    }

}
