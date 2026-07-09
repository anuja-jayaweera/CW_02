package com.example.cw_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomDealers {
    private static final String inputFile = "dealers_cleaned.txt";

    public static List<String> selectDealers(){

        List<String> allDealers = new ArrayList<>();
        List<String> selectedDealers = new ArrayList<>();

        try(BufferedReader Bread = new BufferedReader(new FileReader(inputFile))){

            String line;
            while((line = Bread.readLine()) != null){
                if(!line.trim().isEmpty()){
                    allDealers.add(line.trim());
                }
            }

            if(allDealers.size()<4){
                System.out.println("Not enough dealers in text file!");
                return allDealers;
            }

            Collections.shuffle(allDealers);

            for(int i=0; i < 4; i++){
                selectedDealers.add(allDealers.get(i));
            }
        } catch (IOException e) {
            System.err.println("ERROR! Can't read the text file: " + e.getMessage());
        }
        return selectedDealers;
        }
    }

