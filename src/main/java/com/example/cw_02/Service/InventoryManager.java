package com.example.cw_02.Service;

import java.io.*;

public class InventoryManager {

    private static final String path = "inventory_cleaned.txt";

    public static boolean duplicate(String code) {
        try (BufferedReader Bread = new BufferedReader(new FileReader(path))) {
            String Line;
            while ((Line = Bread.readLine()) != null) {
                String[] data = Line.split("\\|");
                if (data.length > 0 && data[0].trim().equals(code.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }


    public static String addItem(String code, String name, String brand, String price,
                                 String quantity, String category, String date, String image, String threshold) {
        if (duplicate(code)) {
            return "ERROR! Item code exists";
        }

        String record = code + "|" + name + "|" + brand + "|" + price + "|" + quantity + "|" + category + "|" + date + "|" + image + "|" + threshold;

        try (BufferedWriter Bwrite = new BufferedWriter(new FileWriter(path, true))) {
            Bwrite.write(record);
            Bwrite.newLine();
            return "Successfully added item: " + name;
        } catch (IOException e) {
            return "ERROR! Can't write to file: " + e.getMessage();
        }


    }


    public static String deleteItems(String targetCode) {
        File original = new File(path);
        File tempFile = new File("temp.txt");
        boolean exists = false;

        try (BufferedReader Bread = new BufferedReader(new FileReader(original));
             BufferedWriter Bwrite = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = Bread.readLine()) != null) {
                String data[] = line.split("\\|");

                if (data.length > 0 && data[0].trim().equals(targetCode.trim())) {
                    exists = true;
                    continue;
                }

                Bwrite.write(line);
                Bwrite.newLine();
            }
        } catch (IOException e) {
            return "ERROR! Can't Process file: " + e.getMessage();
        }

        if (exists) {
            if (original.delete() && tempFile.renameTo(original)) {
                return "Successfully deleted item " + targetCode;
            } else {
                return "ERROR! Could not update file system";
            }
        } else {
            tempFile.delete();
            return "EROOR! item code does not exists ";
        }
    }

    public static String update(String targetCode., String name, String brand, String price,
                                String quantity, String category, String date, String image, String threshold) {
        File original = new File(path);
        File tempFile = new File("temp.txt");
        boolean exists = false;

        try (BufferedReader Bread = new BufferedReader(new FileReader(original));
             BufferedWriter Bwrite = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = Bread.readLine()) != null) {
                String data[] = line.split("\\|");

                if (data.length > 0 && data[0].trim().equals(targetCode.trim())) {
                    exists = true;

                    line = targetCode + "|" + name + "|" + brand + "|" + price + "|" +
                            quantity + "|" + category + "|" + date + "|" + image + "|" + threshold;
                }

                Bwrite.write(line);
                Bwrite.newLine();
            }
        } catch (IOException e) {
            return "ERROR! can't process file " + e.getMessage();
        }

        if (exists) {
            if (original.delete() && tempFile.renameTo(original)) {
                return "Successfully updated item " + targetCode;
            } else {
                return "ERROR! Could not update file system";
            }
        } else {
            tempFile.delete();
            return "ERROR! Item code does not exist";
        }
    }
}