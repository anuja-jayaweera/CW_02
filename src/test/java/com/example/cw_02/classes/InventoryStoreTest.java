package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryStoreTest {

    private final Path inventoryFile = Paths.get("inventory_cleaned.txt");
    private InventoryStore store;

    @BeforeEach
    void setUp() throws IOException {
        String[] dataLines = {
                "P003|Oil Filter|Bajaj|500.00|10|Engine|2023-10-12|filter.jpg|10",
                "P001|Brake Pad|TVS|1000.00|5|Brakes|2023-05-12|brakepad.png|10",
                "P002|Piston|Bajaj|2000.00|2|Engine|2023-05-12|piston.png|10"
        };
        Files.write(inventoryFile, String.join("\n", dataLines).getBytes());

        store = new InventoryStore();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(inventoryFile);
    }

    @Test
    void testLoadData() {
        store.load();
        ArrayList<PartItem> items = store.getPartItems();

        assertEquals(3, items.size());
        assertEquals("P003", items.get(0).getPartCode());
    }

    @Test
    void testTotalMonetaryValue() {
        store.load();
        double total = store.totalMonetaryValue();
        assertEquals(14000.00, total);
    }

    @Test
    void testSortByCategoryThenCode() {
        store.load();
        store.SortByCategoryThenCode();
        ArrayList<PartItem> items = store.getPartItems();

        assertEquals("Brakes", items.get(0).getCategory());
        assertEquals("P001", items.get(0).getPartCode());

        assertEquals("Engine", items.get(1).getCategory());
        assertEquals("P002", items.get(1).getPartCode());

        assertEquals("Engine", items.get(2).getCategory());
        assertEquals("P003", items.get(2).getPartCode());
    }

    @Test
    void testSaveInventory() throws IOException {
        store.load();

        ArrayList<PartItem> items = store.getPartItems();
        items.get(0).setStock(999);

        store.saveInventory();

        String fileContent = new String(Files.readAllBytes(inventoryFile));
        assertTrue(fileContent.contains("P003|Oil Filter|Bajaj|500.0|999|Engine|"));
    }
}