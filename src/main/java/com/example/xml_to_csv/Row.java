package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

public class Row {
    private final Map<String, String> values = new HashMap<>();

    @JsonAnySetter
    public void setValue(String key, String value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, String> toMap() {
        return values;
    }
}
