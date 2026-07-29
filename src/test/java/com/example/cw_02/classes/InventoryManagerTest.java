package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private final Path inventoryFile = Paths.get("inventory_cleaned.txt");
    private final Path tempFile = Paths.get("temp.txt");
    private final Path auditFile = Paths.get("audit_log.txt");

    @BeforeEach
    void setUp() throws IOException {
        String[] dataLines = {
                "P001|Bajaj Piston|Bajaj|4500.00|15|Engine|2023-10-12|piston.jpg|10",
                "P002|Brake Pad|TVS|1250.00|8|Brakes|2023-05-12|brakepad.png|10"
        };
        Files.write(inventoryFile, String.join("\n", dataLines).getBytes());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(inventoryFile);
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(auditFile);
    }

    @Test
    void testDuplicateFound() {
        assertTrue(InventoryManager.duplicate("P001"), "Should return true for existing code");
    }

    @Test
    void testDuplicateNotFound() {
        assertFalse(InventoryManager.duplicate("P999"), "Should return false for non-existing code");
    }

    @Test
    void testAddItemSuccess() throws IOException {
        String result = InventoryManager.addItem("P003", "Spark Plug", "NGK", "850", "50", "Electrical", "2024-01-05", "spark.jpg", "10");

        assertEquals("Successfully added item: Spark Plug", result);

        String fileContent = new String(Files.readAllBytes(inventoryFile));
        assertTrue(fileContent.contains("P003|Spark Plug|NGK|850|50|Electrical"));
    }

    @Test
    void testAddItemDuplicateCode() {
        String result = InventoryManager.addItem("P001", "Another Piston", "Bajaj", "100", "5", "Engine", "No Date", "No Image", "10");
        assertEquals("ERROR! Item code exists", result);
    }

    @Test
    void testUpdateItemSuccess() throws IOException {
        String result = InventoryManager.update("P001", "Updated Piston", "Bajaj", "5000.00", "20", "Engine", "2023-10-12", "piston.jpg", "10");

        assertEquals("Successfully updated item P001", result);

        String fileContent = new String(Files.readAllBytes(inventoryFile));
        assertTrue(fileContent.contains("Updated Piston"), "File should contain updated name");
        assertTrue(fileContent.contains("5000.00|20"), "File should contain updated price and quantity");
    }

    @Test
    void testUpdateItemNotFound() {
        String result = InventoryManager.update("P999", "Ghost Item", "NA", "0", "0", "NA", "NA", "NA", "0");
        assertEquals("ERROR! Item code does not exist", result);
    }

    @Test
    void testDeleteItemsSuccess() throws IOException {
        String result = InventoryManager.deleteItems("P002");

        assertEquals("Successfully deleted item P002", result);

        String fileContent = new String(Files.readAllBytes(inventoryFile));
        assertFalse(fileContent.contains("P002|Brake Pad"), "Deleted item should no longer be in the file");
    }

    @Test
    void testDeleteItemsNotFound() {
        String result = InventoryManager.deleteItems("P999");
        assertEquals("ERROR! item code does not exists ", result);
    }
}