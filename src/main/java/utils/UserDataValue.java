package utils;

import lombok.Getter;

public enum UserDataValue {
    LOGIN("/login"),
    PASSWORD("/password"),
    ID("/id"),
    TOKEN("/token");

    @Getter
    private final String value;

    UserDataValue(String value) {
        this.value = value;
    }
}