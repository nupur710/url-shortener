package io.urlshortener.short_url.DataAccessLayer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "url_mapping")
public class URL {

    public URL(String longUrl) {
        this.longUrl= longUrl;
    }

    public URL() {}

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
        this.shortUrl = longUrl.split("/")[2];
        return shortUrl;
    }

    public String toString() {
        return "id: " + this.id + "\n long url: " + this.longUrl + "\n short url: " + this.shortUrl;
    }

}
