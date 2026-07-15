package com.example.cw_02.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LowStock {
    public static void lsm(){
        String input = "inventory_cleaned.txt";

        try(BufferedReader Bread = new BufferedReader(new FileReader(input))) {
            String Line;
            while ((Line = Bread.readLine()) != null) {
                if (Line.trim().isEmpty()) continue;

                String[] data = Line.split("\\|");

                String code = data[0];
                String item = data[1];
                int quantity = Integer.parseInt(data[4]);
                int threshold = Integer.parseInt(data[8]);

                if (quantity < threshold) {
                    System.out.printf("WARNING!! Item %s (%s) is in low stock! Current: %d | Threshold: %d \n",
                            code, item, quantity, threshold);
                }

            }
        }
        catch (IOException e){
            System.err.println("Error! file not found!");
        }
    }
}
