package com.project.role_management.utils.openStreetMapApi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate;

    public GeoLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getAddressFromCoordinates(double lat, double lon) {

        String url = UriComponentsBuilder
                .fromHttpUrl("https://nominatim.openstreetmap.org/reverse")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("format", "jsonv2")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "RoleManagementApp/1.0 (contact: gunjan.dev@gmail.com)");
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }

}
