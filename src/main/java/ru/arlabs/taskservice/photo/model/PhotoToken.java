package ru.arlabs.taskservice.photo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
@AllArgsConstructor
public class PhotoToken {
    private String signature;
    private long expire;
}
