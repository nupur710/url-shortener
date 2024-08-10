package io.urlshortener.short_url.DataAccessLayer;

import io.urlshortener.short_url.Hashing.SHA256;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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
        SHA256 sha256= new SHA256();
        this.shortUrl = sha256.chunkLoops(longUrl);
        return shortUrl;
    }

    public String toString() {
        return "id: " + this.id + "\n long url: " + this.longUrl + "\n short url: " + this.shortUrl;
    }

}
