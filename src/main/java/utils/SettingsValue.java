package utils;

import lombok.Getter;

public enum SettingsValue {
    BASE_REQUEST("/base_request"),
    VERSION_API("/version_api");

    @Getter
    private final String value;

    SettingsValue(String value) {
        this.value = value;
    }
}