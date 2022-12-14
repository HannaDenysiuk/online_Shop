package com.example.online_shop.data;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String brand;
    private String size;
    private Double price;

    public Product() {
    }

    public Product(String name, String category, String brand, String size, Double price) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.size = size;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  name + " by " + brand + ", size: " + size + ", price: " + price + "$";
    }
}
