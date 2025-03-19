package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Header {
    @JsonProperty("Columns")
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }
}
