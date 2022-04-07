package ru.arlabs.taskservice.photo;

import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.photo.model.PhotoToken;

import java.time.Duration;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface PhotoService {

    UUID upload(MultipartFile file);

    PhotoToken generate(Duration duration);

    void delete(UUID uuid);
}
