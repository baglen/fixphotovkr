package ru.arlabs.taskservice.photo.internal;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.photo.PhotoService;
import ru.arlabs.taskservice.photo.model.PhotoToken;
import ru.arlabs.taskservice.photo.properties.PhotoProperties;
import ru.arlabs.taskservice.photo.response.PhotoUploadResponse;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Jeb
 */
@Service
public class DefaultPhotoService implements PhotoService {
    private final RestTemplate restTemplate;
    private final PhotoProperties properties;

    @Autowired
    public DefaultPhotoService(PhotoProperties properties, RestTemplateBuilder builder) {
        this.properties = properties;
        this.restTemplate = builder.rootUri(properties.getBaseUrl())
                .defaultHeader("X-Photo-API", properties.getApiKey())
                .build();
    }

    @Override
    public UUID upload(MultipartFile file) {
        final PhotoToken token = this.generate(Duration.ofDays(1));

        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("image", file.getResource());
        map.add("signature", token.getSignature());
        map.add("expire", token.getExpire());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        final PhotoUploadResponse response = restTemplate.postForObject("/upload", requestEntity, PhotoUploadResponse.class);

        return response.getId();
    }

    @Override
    public PhotoToken generate(Duration duration) {
        final Instant expireInstant = Instant.now(Clock.systemUTC())
                .plus(duration);
        final long expire = expireInstant.getEpochSecond();
        final String signature = Hashing.hmacSha256(properties.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .hashString(String.valueOf(expire), StandardCharsets.UTF_8)
                .toString();
        return new PhotoToken(signature, expire);
    }

    @Override
    public void delete(UUID uuid) {
        restTemplate.delete("/delete/{uuid}", uuid);
    }
}
