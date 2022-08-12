package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SavedPhoto {
    @JsonProperty("album_id")
    private int albumId;
    private int date;
    private int id;
    @JsonProperty("owner_id")
    private int ownerId;
    @JsonProperty("has_tags")
    private boolean hasTags;
    @JsonProperty("access_key")
    private String accessKey;
    private List<PhotoSize> sizes;
    private String text;
}