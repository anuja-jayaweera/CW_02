package com.example.cw_02;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataCleaner {

    public static void inventory() {
        String Input = "inventory_legacy.txt";
        String Output = "inventory_cleaned.txt";
        int threshold = 10;


        try (BufferedReader Bread = new BufferedReader(new FileReader(Input));
             BufferedWriter Bwrite = new BufferedWriter(new FileWriter(Output))) {
            String line;
            while ((line = Bread.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split("[,;|]", -1);
                String code = data.length > 0 ? data[0].trim() : "";
                String item = data.length > 1 ? data[1].trim() : "unknown";
                String brand = data.length > 2 && !data[2].trim().isEmpty() ? data[2].trim() : "Unknown";
                String rPrice = data.length > 3 ? data[3].trim() : "Rs.0.00";
                String Qty = data.length > 4 ? data[4].trim() : "0";
                String category = data.length > 5 ? data[5].trim() : "General";
                String rdate = data.length > 6 ? data[6].trim() : "";
                String image = data.length > 7 && !data[7].trim().isEmpty() ? data[7].trim() : "No Image";

                String cprice = rPrice.replace("Rs.", "").replace("Rs", "").replace(",", "").trim();
                double price = cprice.isEmpty() ? 0.00 : Double.parseDouble(cprice);

                int quantity = Qty.isEmpty() ? 0 : Integer.parseInt(Qty);

                if (!category.isEmpty()) {
                    category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
                }

                DateTimeFormatter standard = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter[] formatters = {
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                        DateTimeFormatter.ofPattern("MMM dd,yyyy", Locale.ENGLISH),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH),
                };

                String newDate = "No Date";
                if (!rdate.isEmpty()) {
                    for (DateTimeFormatter formatter : formatters) {
                        try {
                            LocalDate parsedDate = LocalDate.parse(rdate, formatter);
                            newDate = parsedDate.format(standard);
                            break;
                        } catch (Exception e) {

                        }
                    }
                }
                String cleanLine = String.format("%s|%s|%s|%.2f|%d|%s|%s|%s|%d",
                        code, item, brand, price, quantity, category, newDate, image, threshold);

                Bwrite.write(cleanLine);
                Bwrite.newLine();
            }
            System.out.println("Data Cleaning complete! new inventory file is saved as: " + Output);
        } catch (IOException e) {
            System.err.println("Error! can't process the file: " + e.getMessage());
        }


    }

    public static void dealers() {
        String input = "dealers_legacy.txt";
        String output = "dealers_cleaned.txt";

        try (BufferedReader Bread = new BufferedReader(new FileReader(input));
             BufferedWriter Bwrite = new BufferedWriter(new FileWriter(output))) {

            String line;
            while ((line = Bread.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String normalizedLine = line.replace(",", "|").replace(";", "|");
                String[] data = normalizedLine.split("\\|");


                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();

                    if (data[i].isEmpty()) {
                        data[i] = "Unknown";
                    }
                }

                String newRecord = String.join("|", data);

                Bwrite.write(newRecord);
                Bwrite.newLine();
            }

            System.out.println("Data Cleaning complete! new dealers text file is saved as: " + output);

        } catch (IOException e) {
            System.out.println("ERROR!! Could not process file " + e.getMessage());
        }
    }

}