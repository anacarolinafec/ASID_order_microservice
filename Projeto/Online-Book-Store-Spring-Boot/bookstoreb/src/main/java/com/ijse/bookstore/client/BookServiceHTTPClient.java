package com.ijse.bookstore.client;

import com.ijse.bookstore.dto.BookResponseDTO;
import com.ijse.bookstore.dto.CartResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class BookServiceHTTPClient {
    private final RestTemplate restTemplate;
    private final String bookServiceUrl;

    public BookServiceHTTPClient(RestTemplate restTemplate,
                                 @Value("${book.service.url}") String bookServiceUrl) {
        this.restTemplate = restTemplate;
        this.bookServiceUrl = bookServiceUrl;
    }

    public Optional<BookResponseDTO> getBookbyId(Long userId) {
        log.info("Fetching book of user with ID: {}", userId);
        String url = bookServiceUrl + "/books/" + userId;

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, BookResponseDTO.class));
        } catch (Exception e) {
            log.error("Error fetching cart of user with with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}