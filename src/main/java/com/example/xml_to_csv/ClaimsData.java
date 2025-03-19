package com.example.xml_to_csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconocidos como <File>
public class ClaimsData {
    
    @JsonProperty("Header")
    private Header header;

    @JsonProperty("Rows")
    private Rows rows;

    // Constructor vacío necesario para Jackson
    public ClaimsData() {
        this.header = new Header();  // Evita null pointer
        this.rows = new Rows();      // Evita null pointer
    }

    public String[] getHeaders() {
        if (header == null || header.getColumns() == null) {
            return new String[0]; // Retorna un array vacío si no hay header
        }
        return header.getColumns().stream()
                .map(Column::getName)
                .toArray(String[]::new);
    }

    public List<Map<String, String>> getRows() {
        if (rows == null || rows.getRowData().isEmpty()) {
            return List.of(); // Retorna una lista vacía si no hay rows
        }
        return rows.getRowData();
    }
}
