package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataCleanerTest {

    private final Path inventoryInput = Paths.get("inventory_legacy.txt");
    private final Path inventoryOutput = Paths.get("inventory_cleaned.txt");
    private final Path dealersInput = Paths.get("dealers_legacy.txt");
    private final Path dealersOutput = Paths.get("dealers_cleaned.txt");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(inventoryInput);
        Files.deleteIfExists(inventoryOutput);
        Files.deleteIfExists(dealersInput);
        Files.deleteIfExists(dealersOutput);
    }

    @Test
    void testInventoryCleaning() throws IOException {
        String dirtyInventoryData = "ITEM01|Spark Plug||Rs.1500.50||engine|12/05/2023|";
        Files.write(inventoryInput, dirtyInventoryData.getBytes());

        DataCleaner.inventory();

        assertTrue(Files.exists(inventoryOutput), "Cleaned inventory file should be created");

        List<String> cleanedLines = Files.readAllLines(inventoryOutput);
        assertEquals(1, cleanedLines.size(), "Should have exactly one processed line");

        String cleanedLine = cleanedLines.get(0);
        String expectedLine = "ITEM01|Spark Plug|Unknown|1500.50|0|Engine|2023-05-12|No Image|10";
        assertEquals(expectedLine, cleanedLine, "The cleaned line should match the expected formatted output");
    }

    @Test
    void testDealersCleaning() throws IOException {
        String dirtyDealerData = "D01;John Doe,, Colombo";
        Files.write(dealersInput, dirtyDealerData.getBytes());

        DataCleaner.dealers();

        assertTrue(Files.exists(dealersOutput), "Cleaned dealers file should be created");

        List<String> cleanedLines = Files.readAllLines(dealersOutput);
        assertEquals(1, cleanedLines.size(), "Should have exactly one processed line");

        String cleanedLine = cleanedLines.get(0);
        String expectedLine = "D01|John Doe|Unknown|Colombo";
        assertEquals(expectedLine, cleanedLine, "The cleaned line should match the expected formatted output");
    }
}