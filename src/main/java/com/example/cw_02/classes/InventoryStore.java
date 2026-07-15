package com.example.cw_02.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryStore {
    private static final String path = "inventory_cleaned";
    private ArrayList<PartItem> partItems = new ArrayList<PartItem>();

    public ArrayList<PartItem> getPartItems() {
        return partItems;
    }

    public void load() {
        partItems.clear();

        try (BufferedReader Bread = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = Bread.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String data[] = line.split("\\|" ,-1);

                if(data.length<9){
                    continue;
                }

                try {
                    PartItem partItem = new PartItem(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            Double.parseDouble(data[3].trim()),
                            Integer.parseInt(data[4].trim()),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            Integer.parseInt(data[8].trim())
                    );
                    partItems.add(partItem);
                } catch (Exception e) {
                }
            }
        }catch (IOException e) {
            System.err.println("Error loading inventory file " + e.getMessage());
        }
    }
    public void SortByCategoryThenCode(){
        for(int i = 0; i < partItems.size() - 1; i++){
            for(int j = 0; j < partItems.size() - 1 - i; j++){
                PartItem a = partItems.get(j);
                PartItem b = partItems.get(j + 1);

                int categoryCompare = a.getCategory().compareToIgnoreCase(b.getCategory());
                boolean needSwap = false;

                if(categoryCompare>0){
                    needSwap = true;
                } else if (categoryCompare == 0) {
                    if(a.getPartCode().compareToIgnoreCase(b.getPartCode()) > 0){
                        needSwap = true;
                    }
                }if(needSwap){
                    partItems.set(j,b);
                    partItems.set(j + 1,a);
                }

            }
        }
    }

    public ArrayList<PartItem> viewInventory(){
        load();
        SortByCategoryThenCode();
        return partItems;
    }

    public double totalMonetaryValue(){
        double total = 0;
        for(int i = 0; i <partItems.size(); i++){
            PartItem p = partItems.get(i);
            total = total + p.getPrice() * p.getStock();
        }
        return total;
    }
}