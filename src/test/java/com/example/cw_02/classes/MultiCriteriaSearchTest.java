package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MultiCriteriaSearchTest {
    private final Path testInventoryFile = Paths.get("inventory_cleaned.txt");

    @BeforeEach
    void setUp() throws IOException{
        String [] dataLines = {
                "P001|Bajaj 4-Stroke Piston|Bajaj|4500.00|15|Engine|2023-10-12|piston4s.jpg|10",
                "P002|TVS King Brake Pad|TVS|1250.00|8|Brakes|2023-05-12|brakepad.png|10",
                "P004|Spark Plug NGK|NGK|850.00|50|Electrical|2024-01-05|spark.jpg|10",
                "P005|Clutch Cable Bajaj RE|Bajaj|950.00|12|Engine|2024-02-01|cl_cable.jpg|10"
        };
        Files.write(testInventoryFile,String.join("\n",dataLines).getBytes());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testInventoryFile);
    }

    @Test
    void tesPerformSearchMatchFound(){
        String result = MultiCriteriaSearch.performSearch("Engine", 900.00, 1000.00, "Cable");

        assertTrue(result.contains("Code: P005"), "Should contain matched code");
        assertTrue(result.contains("Name: Clutch Cable Bajaj RE"), "Should contain matched name");
        assertTrue(result.contains("Price: 950.0"), "Should contain matched price");
        assertFalse(result.contains("P001"), "Should not contain items outside the price range");
    }

    @Test
    void testPerformSearchNoMatchFound() {

        String result = MultiCriteriaSearch.performSearch("Electrical", 10.00, 5000.00, "Piston");


        assertEquals("No matching Item Found", result, "Should return no matching item message");
    }

    @Test
    void testPerformSearchCaseInsensitive() {

        String result = MultiCriteriaSearch.performSearch("eLeCtRiCaL", 500.00, 1000.00, "pLuG");

        assertTrue(result.contains("Code: P004"), "Should match regardless of casing in category or keyword");
        assertTrue(result.contains("Name: Spark Plug NGK"), "Should return correct item details");
    }

    @Test
    void testPerformSearchFileError() throws IOException {

        Files.deleteIfExists(testInventoryFile);

        String result = MultiCriteriaSearch.performSearch("Engine", 1000.00, 5000.00, "Plug");

        assertTrue(result.startsWith("ERROR! can't read file:"), "Should return the formatted error message upon IOException");
    }
}