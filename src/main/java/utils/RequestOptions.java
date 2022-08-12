package utils;

import lombok.Getter;

public enum RequestOptions {
    USER_ID("user_id"),
    MESSAGE("message"),
    PHOTO("photo"),
    SERVER("server"),
    HASH("hash"),
    OWNER_ID("owner_id"),
    POST_ID("post_id"),
    ATTACHMENTS("attachments"),
    TYPE("type"),
    POST("post"),
    ITEM_ID("item_id"),
    NOTHING(""),
    RESPONSE("response"),
    ACCESS_TOKEN("access_token"),
    V("v");

    @Getter
    private final String value;

    RequestOptions(String value) {
        this.value = value;
    }
}