package com.ijse.bookstore.CommandModel.client;

import com.ijse.bookstore.CommandModel.dto.CartResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class CartServiceHTTPClient {
    private final RestTemplate restTemplate;
    private final String cartServiceUrl;

    public CartServiceHTTPClient(RestTemplate restTemplate,
                                 @Value("${cart.service.url}") String cartServiceUrl) {
        this.restTemplate = restTemplate;
        this.cartServiceUrl = cartServiceUrl;
    }

    public Optional<CartResponseDTO> getCartOfUserId(Long userId) {
        log.info("Fetching cart of user with ID: {}", userId);
        String url = cartServiceUrl + "/cart/" + userId;

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, CartResponseDTO.class));
        } catch (Exception e) {
            log.error("Error fetching cart of user with with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}