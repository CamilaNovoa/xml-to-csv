package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rows {
    @JsonProperty("Row")
    private List<Row> rowList;

    public List<Map<String, String>> getRowData() {
        return rowList.stream()
                .map(Row::toMap)
                .collect(Collectors.toList());
    }
}
