package utils;

import static common.Constants.properties;

public class Property {
    private String key;

    public Property(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String get() {
        return properties.getProperty(getKey());
    }
}
