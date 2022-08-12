package models;

import lombok.Data;

@Data
public class UploadedPhoto {
    private int server;
    private String photo;
    private String hash;
}