package io.urlshortener.short_url.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, String> {
    URL findByShortUrl(String s);
    URL findByLongUrl(String s);
}
