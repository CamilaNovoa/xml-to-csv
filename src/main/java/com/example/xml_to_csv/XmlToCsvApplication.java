package com.example.xml_to_csv;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class XmlToCsvApplication {

	private static final Logger logger = LoggerFactory.getLogger(XmlToCsvApplication.class);
    private static final List<String> PII_COLUMNS = List.of(
            "PrimaryLastName", "PrimaryFirstName", "PrimaryMiddleName",
            "PrimaryDob", "PrimaryGender", "PatientLastName",
            "PatientFirstName", "PatientMiddleName", "PatientDob",
            "PatientGender", "MailAddress1", "MailAddress2"
    );
	public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Use: java -jar xml-to-csv.jar --input=archivo.xml --output=archivo.csv");
            System.exit(1);
        }

        String inputFile = args[0].replace("--input=", "");
        String outputFile = args[1].replace("--output=", "");

        try {
            convertXmlToCsv(inputFile, outputFile);
            logger.info("Conversion complete: {}", outputFile);
        } catch (Exception e) {
            logger.error("Error conversion: {}", e.getMessage());
        }
    }

    public static void convertXmlToCsv(String xmlPath, String csvPath) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            ClaimsData claimsData = xmlMapper.readValue(new File(xmlPath), ClaimsData.class);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath));
                 @SuppressWarnings("deprecation")
				 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(claimsData.getHeaders()))) {

                for (Map<String, String> row : claimsData.getRows()) {
                    csvPrinter.printRecord(processRow(row).values());
                }
            }

            logger.info("file CSV generated: {}", csvPath);

        } catch (Exception e) {
            logger.error("Error proccesing {}: {}", xmlPath, e.getMessage());
        }
    }

    private static Map<String, String> processRow(Map<String, String> row) {
        return row.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> PII_COLUMNS.contains(e.getKey()) ? hashValue(e.getValue()) : e.getValue()
                ));
    }

    private static String hashValue(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
