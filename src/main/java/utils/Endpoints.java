package utils;

import lombok.Getter;

public enum Endpoints {
    POST("wall.post"),
    EDIT("wall.edit"),
    UPLOAD_PHOTO("photos.getWallUploadServer"),
    SAVE_PHOTO("photos.saveWallPhoto"),
    ADD_COMMENT("wall.createComment"),
    IS_LIKED("likes.isLiked"),
    DELETE("wall.delete");

    @Getter
    private final String value;

    Endpoints(String value) {
        this.value = value;
    }
}