package com.example.cw_02.Service;

public class PartItem {
    private String partCode;
    private String name;
    private String brand;
    private double price;
    private int stock;
    private String category;
    private String date;
    private String imageFile;
    private int status;

    public PartItem(String partCode, String name, String brand, double price, int stock, String category, String date, String imageFile, int status){
        this.partCode = partCode;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.date = date;
        this.imageFile = imageFile;
        this.status = status;
    }

    public String getPartCode(){
        return partCode;
    }

    public String getName(){
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getImageFile() {
        return imageFile;
    }

    public int getStatus() {
        return status;
    }
}
