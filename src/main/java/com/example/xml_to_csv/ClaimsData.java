package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class ClaimsData {
    
    @JsonProperty("Header")
    private Header header;

    @JsonProperty("Rows")
    private Rows rows;

    
    public ClaimsData() {
        this.header = new Header();  
        this.rows = new Rows();      
    }

    public String[] getHeaders() {
        if (header == null || header.getColumns() == null) {
            return new String[0]; 
        }
        return header.getColumns().stream()
                .map(Column::getName)
                .toArray(String[]::new);
    }

    public List<Map<String, String>> getRows() {
        if (rows == null || rows.getRowData().isEmpty()) {
            return List.of(); 
        }
        return rows.getRowData();
    }
}
