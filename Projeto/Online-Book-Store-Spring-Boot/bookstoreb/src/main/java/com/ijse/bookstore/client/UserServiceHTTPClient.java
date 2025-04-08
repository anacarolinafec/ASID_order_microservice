package com.ijse.bookstore.client;

import com.ijse.bookstore.dto.UserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceHTTPClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceHTTPClient(RestTemplate restTemplate,
                                 @Value("${user.service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public Optional<UserResponseDTO> getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        String url = userServiceUrl + "/id/" + userId;

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, UserResponseDTO.class));
        } catch (Exception e) {
            log.error("Error fetching user with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}