package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomDealersTest {

    private final String testFileName = "dealers_cleaned.txt";

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(testFileName)) {
            writer.write("D101|Sunil Motors|0771234567|Malabe\n");
            writer.write("D102|Kaduwela Spares Hub|0719876543|Kaduwela\n");
            writer.write("D103|Ranatunga Auto|Unknown|Pittugala\n");
            writer.write("D104|Maharagama Tuk Parts|0705556666|Maharagama\n");
            writer.write("D106|Athurugiriya Auto|0721112222|Athurugiriya\n");
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSelectDealers() {
        List<String> dealers = RandomDealers.selectDealers();

        assertNotNull(dealers);
        assertEquals(4, dealers.size(), "Should return exactly 4 dealers");

        String prevLocation = "";
        for (String dealer : dealers) {
            String[] data = dealer.split("\\|");
            String currentLocation = data[3].trim();

            assertTrue(currentLocation.compareToIgnoreCase(prevLocation) >= 0,
                    "Dealers are not sorted correctly by location");

            prevLocation = currentLocation;
        }
    }
}

