package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LowStockTest {

    private final String testFileName = "inventory_cleaned.txt";

    @AfterEach
    void tearDown() {
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createInventoryFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(testFileName)) {
            writer.write(content);
        }
    }

    @Test
    void testLsmWithProvidedInventoryData() throws IOException {
        String providedData =
                        "P001|Bajaj 4-Stroke Piston|Bajaj|4500.00|15|Engine|2023-10-12|piston4s.jpg|10\n" +
                        "P002|TVS King Brake Pad|TVS|1250.00|8|Brakes|2023-05-12|brakepad.png|10\n" +
                        "P003|205/50-10 Tyre|Unknown|6500.00|24|Bodywork|No Date|2023|10\n" +
                        "P004|Spark Plug NGK|NGK|850.00|50|Electrical|2024-01-05|spark.jpg|10\n" +
                        "P005|Clutch Cable Bajaj RE|Bajaj|950.00|12|Engine|2024-02-01|cl_cable.jpg|10\n" +
                        "P006|Headlight Bulb 12V|Unknown|450.00|30|Electrical|No Date|hl_bulb.jpg|10\n" +
                        "P007|3-Wheeler Canopy Cover|Local|8500.50|5|Bodywork|No Date|canopy.png|10\n" +
                        "P008|Piaggio Ape Filter|Piaggio|1100.00|0|Engine|2024-02-28|filter_ape.jpeg|10\n" +
                        "P009|Ignition Coil 2-Stroke|Bajaj|2200.00|18|Electrical|2023-09-01|No Image|10\n" +
                        "P010|Rear View Mirror|Unknown|800.00|45|Bodywork|2023-10-10|mirror.png|10\n";
        createInventoryFile(providedData);
        String result = LowStock.lsm();
        String expected = "WARNING! Item TVS King Brake Pad is on low stock!\nCurrent Stock: 8";
        assertEquals(expected, result);
    }

    @Test
    void testLsmWithNoLowStockItems() throws IOException {
        String noLowStockData =
                        "P001|Bajaj 4-Stroke Piston|Bajaj|4500.00|15|Engine|2023-10-12|piston4s.jpg|10\n" +
                        "P002|TVS King Brake Pad|TVS|1250.00|12|Brakes|2023-05-12|brakepad.png|10\n";
        createInventoryFile(noLowStockData);
        String result = LowStock.lsm();
        assertEquals("No low stock items detected.", result);
    }
}