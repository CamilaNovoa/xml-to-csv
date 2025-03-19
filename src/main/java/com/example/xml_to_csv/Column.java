package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }
}
