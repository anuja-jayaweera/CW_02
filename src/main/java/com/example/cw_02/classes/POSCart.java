package com.example.cw_02.classes;

public class POSCart {
    public static String checkout(String[] ids, String[] names, String [] categories, double[] prices, int[] quantities, int[] stockLevels) {
        //prevents empty carts form processing to check out
        if (ids == null || ids.length == 0) {
            return "ERROR! Cant process an empty cart";
        }

        double totalAfterDiscount = 0.0;
        boolean hasEngine = false;
        boolean hasElectrical = false;
        String itemizedBreakdown = "";

        for (int i = 0; i < ids.length; i++) {
            int qty = quantities[i];
            int stock = stockLevels[i];
            double price = prices[i];
            String category = categories[i];

            //prevent zero or negative quantities
            if (qty <= 0) {
                return "ERROR! Invalid quantity, quantity should be grater than 0";
            }

            //prevent selling more items than in stock
            if (qty > stock) {
                return "ERROR! stock insufficient";
            }

            //checks if specific categories exists in cart
            if (category.equalsIgnoreCase("Engine")) {
                hasEngine = true;
            }
            if (category.equalsIgnoreCase("Electrical")) {
                hasElectrical = true;
            }

            //calculate the standard subtotal
            double subTotal = price * qty;
            String discountAppliedText = "None";

            //business rule 1(5% discount if the item quantity is greater than 3)
            if (qty >= 3) {
                subTotal = subTotal * 0.95;
                discountAppliedText = "5% bulk discount";
            }
            itemizedBreakdown += names[i] + "X" + qty + "|Subtotal Rs: " + String.format("%2f", subTotal) + "(" + discountAppliedText + ")\n";

            totalAfterDiscount += subTotal;
        }
        //business rule 2 (10% discount if Engine and Electrical category is included)
            double finalTotal = totalAfterDiscount;
            String synergyText = "No";

            if (hasEngine && hasElectrical) {
                finalTotal = totalAfterDiscount * 0.90;
                synergyText = "10% off discount applied";
            }

            String receipt = "=====Transaction Receipt=====\n" +
                    itemizedBreakdown +
                    "------------------------------------------\n" +
                    "Synergy Discount: " + synergyText + "\n" +
                    "Total due amount: " + String.format("%2f", finalTotal) + "\n" +
                    "===========================================";
            return receipt;
        }
}