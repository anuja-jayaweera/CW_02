package com.example.cw_02.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDealers {
    private static final String inputFile = "dealers_cleaned.txt";

    public static List<String> selectDealers() {
        List<String> allDealers = new ArrayList<>();
        List<String> selectedDealers = new ArrayList<>();

        try (BufferedReader Bread = new BufferedReader(new FileReader(inputFile))) {

            String line;
            while ((line = Bread.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allDealers.add(line.trim());
                }
            }

            if (allDealers.size() < 4) {
                System.out.println("Not enough dealers in text file!");
                sortDealersByLocation(allDealers);
                return allDealers;
            }

            Random random = new Random();
            List<String> tempDealers = new ArrayList<>(allDealers);

            for (int i = 0; i < 4; i++) {
                int randomIndex = random.nextInt(tempDealers.size());
                selectedDealers.add(tempDealers.get(randomIndex));
                tempDealers.remove(randomIndex);
            }

            sortDealersByLocation(selectedDealers);

        } catch (IOException e) {
            System.err.println("ERROR! Can't read the text file: " + e.getMessage());
        }
        return selectedDealers;
    }

    private static void sortDealersByLocation(List<String> dealers) {
        for (int i = 0; i < dealers.size() - 1; i++) {
            for (int j = 0; j < dealers.size() - 1 - i; j++) {

                String dealer1 = dealers.get(j);
                String dealer2 = dealers.get(j + 1);

                String[] data1 = dealer1.split("\\|", -1);
                String[] data2 = dealer2.split("\\|", -1);

                String location1 = data1.length > 3 ? data1[3].trim() : "";
                String location2 = data2.length > 3 ? data2[3].trim() : "";

                if (location1.compareToIgnoreCase(location2) > 0) {
                    dealers.set(j, dealer2);
                    dealers.set(j + 1, dealer1);
                }
            }
        }
    }
}