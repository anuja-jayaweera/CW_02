package com.example.cw_02;

import java.io.*;
import java.util.Scanner;

public class InventoryManager {
    private static final String path = "inventory_cleaned.txt";

    public static void addItem() {
        Scanner scanner = new Scanner(System.in);
        boolean exists = true;
        String code="";
        while (exists) {
            System.out.println("Enter item Code: ");
            code = scanner.nextLine().trim();
            exists = false;

            try (BufferedReader Bread = new BufferedReader(new FileReader(path))) {
                String Line;
                while ((Line = Bread.readLine()) != null) {
                    String[] data = Line.split("\\|");
                    if (data[0].trim().equals(code.trim())) {
                        System.out.println("Duplicate Found!");
                        exists = true;
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("ERROR!!: Could not read file!");
            }
            System.out.println();
        }
        System.out.println("Enter item Name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter item Brand: ");
        String brand = scanner.nextLine().trim();

        System.out.println("Enter item Price: ");
        String price = scanner.nextLine().trim();

        System.out.println("Enter item Quantity: ");
        String quantity = scanner.nextLine().trim();

        System.out.println("Enter item Category: ");
        String category = scanner.nextLine().trim();

        System.out.println("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.println("Enter item image file Name: ");
        String image = scanner.nextLine().trim();

        System.out.println("Enter item Threshold: ");
        String threshold = scanner.nextLine().trim();

        String record = code + "|" + name + "|" + brand + "|" + price + "|" + quantity + "|" + category + "|" + date + "|" + image + "|" + threshold;

        try (BufferedWriter Bwrite = new BufferedWriter(new FileWriter(path, true))) {
            Bwrite.write(record);
            Bwrite.newLine();
            System.out.println("Successfully added item: " + name);
        } catch (IOException e) {
            System.err.println("ERROR!! Can't write to file" + e.getMessage());
        }
    }
    public static void deleteItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the code of the item that you want to delete: ");
        String targetCode = scanner.nextLine().trim();

        File original = new File(path);
        File temp = new File("temp.txt");

        boolean exists = false;
        try (BufferedReader Bread = new BufferedReader(new FileReader(original));
             BufferedWriter Bwrite = new BufferedWriter(new FileWriter(temp))){
            String line;
            while ((line = Bread.readLine())!= null){
                String data [] = line.split("\\|");

                if(data.length>0&&data[0].trim().equals(targetCode)){
                    exists = true;
                    continue;
                }

                Bwrite.write(line);
                Bwrite.newLine();
            }
        } catch (IOException e){
            System.out.println("ERROR! Can't process file" +e.getMessage());
            return;
        }
        if (exists){
            if(original.delete()){
                if(temp.renameTo(original)){
                    System.out.println("Successfully deleted item "+targetCode);
                }else {
                    System.out.println("ERROR! Could not update file system");
                }
            }else {
                System.out.println("ERROR! Deletion failed");
            }
        }else {
            System.out.println("ERROR! Item code does not exists");
            temp.delete();
        }
    }
}
