package com.example.cw_02.classes;

import com.sun.jdi.event.StepEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String fileName = "audit_log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logAction(String action, String itemCode, String quantity){
        String timeStamp = LocalDateTime.now().format(formatter);

        //formatting the log entry
        String logEntry = timeStamp + "| Action: " + action + "| Item Code: " + itemCode + "| Quantity: " + quantity;

        //write to the "audit_log.txt" file
        try(BufferedWriter Bwrite = new BufferedWriter(new FileWriter(fileName,true))){
            Bwrite.write(logEntry);
            Bwrite.newLine();
        }catch (IOException e){
            System.err.println("ERROR! cannot write to "+ fileName + e.getMessage());
        }
    }
}
