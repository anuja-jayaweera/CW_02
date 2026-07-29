package com.example.cw_02.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuditLoggerTest {

    private final String testFileName = "audit_log.txt";

    @BeforeEach
    void setUp() {
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
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
    void testLogAction() throws IOException {
        AuditLogger.logAction("UPDATE", "I005", "25");

        File file = new File(testFileName);
        assertTrue(file.exists(), "The audit log file should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String logEntry = reader.readLine();

            assertNotNull(logEntry, "The log file should not be empty");

            String expectedDataString = "| Action: UPDATE| Item Code: I005| Quantity: 25";
            assertTrue(logEntry.contains(expectedDataString),
                    "Log entry does not contain the expected data. Actual: " + logEntry);
        }
    }
}