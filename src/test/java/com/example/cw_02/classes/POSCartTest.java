package com.example.cw_02.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class POSCartTest {

    @Test
    void testEmptyCart() {

        String[] emptyIds = new String[0];
        String result = POSCart.checkout(emptyIds, new String[0], new String[0], new double[0], new int[0], new int[0]);

        assertEquals("ERROR! Cant process an empty cart", result, "Should reject empty cart");
    }

    @Test
    void testInvalidQuantity() {

        String[] ids = {"P001"};
        String[] names = {"Spark Plug"};
        String[] categories = {"Engine"};
        double[] prices = {500.0};
        int[] quantities = {0}; // Invalid quantity
        int[] stockLevels = {10};

        String result = POSCart.checkout(ids, names, categories, prices, quantities, stockLevels);

        assertEquals("ERROR! Invalid quantity, quantity should be grater than 0", result);
    }

    @Test
    void testInsufficientStock() {

        String[] ids = {"P001"};
        String[] names = {"Spark Plug"};
        String[] categories = {"Engine"};
        double[] prices = {500.0};
        int[] quantities = {15}; // Ordering 15
        int[] stockLevels = {10}; // Only 10 in stock

        String result = POSCart.checkout(ids, names, categories, prices, quantities, stockLevels);

        assertEquals("ERROR! stock insufficient", result);
    }

    @Test
    void testStandardCheckoutNoDiscounts() {

        String[] ids = {"P002"};
        String[] names = {"Brake Pads"};
        String[] categories = {"General"};
        double[] prices = {1000.0};
        int[] quantities = {2};
        int[] stockLevels = {10};

        String result = POSCart.checkout(ids, names, categories, prices, quantities, stockLevels);


        assertTrue(result.contains("Total due amount: 2000.0"), "Receipt should show 2000.0 total");
        assertTrue(result.contains("Synergy Discount: No"), "Synergy discount should not apply");
    }

    @Test
    void testBulkDiscount() {

        String[] ids = {"P003"};
        String[] names = {"Oil Filter"};
        String[] categories = {"General"};
        double[] prices = {1000.0};
        int[] quantities = {3};
        int[] stockLevels = {10};

        String result = POSCart.checkout(ids, names, categories, prices, quantities, stockLevels);


        assertTrue(result.contains("5% bulk discount"), "Should show bulk discount applied");
        assertTrue(result.contains("2850.0"), "Total should reflect 5% discount");
    }

    @Test
    void testSynergyDiscount() {

        String[] ids = {"E001", "EL001"};
        String[] names = {"Piston", "Battery"};
        String[] categories = {"Engine", "Electrical"};
        double[] prices = {2000.0, 3000.0};
        int[] quantities = {1, 1};
        int[] stockLevels = {10, 10};

        String result = POSCart.checkout(ids, names, categories, prices, quantities, stockLevels);


        assertTrue(result.contains("10% off discount applied"), "Should show synergy discount applied");
        assertTrue(result.contains("4500.0"), "Total should reflect 10% overall discount");
    }
}