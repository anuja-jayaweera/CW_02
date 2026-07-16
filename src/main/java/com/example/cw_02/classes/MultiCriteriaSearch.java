package com.example.cw_02.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MultiCriteriaSearch {

    public static String performSearch(String searchCategory, double minPrice, double maxPrice, String keyword){
        String path = "Inventory_cleaned.txt";
        String results = "";
        try(BufferedReader Bread = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = Bread.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                String data[] = line.split("\\|");

                String name = data[1];
                double price = Double.parseDouble(data[3]);
                String category = data[5];

                boolean categoryMatch = category.equalsIgnoreCase(searchCategory);
                boolean priceMatch = (price >= minPrice) && (price <= maxPrice );
                boolean keywordMatch = name.toLowerCase().contains(keyword.toLowerCase());

                if(categoryMatch && priceMatch && keywordMatch){
                    results += "Code: " + data[0] + "| Name: " + name + "| Price: " + price + "| Category: " + category +"\n";
                }
            }
        }catch (IOException e){
            return "ERROR! can't read file: " + e.getMessage();
        }
        if(results.isEmpty()){
            return "No matching Item Found";
        }
        return results;
    }
}
