package com.example.online_shop.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IRepository{
    private String fileName;
    private List<Product> products;

    public ProductRepository(String fileName) {
        this.fileName = fileName;
        this.products = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        try {
            reloadData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return products;
    }
    private void reloadData() {
        try(FileInputStream stream = new FileInputStream(fileName)){
            try(ObjectInputStream reader = new ObjectInputStream(stream)){
                products = (List<Product>) reader.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getByBrand(String brand) {
       return getAll().stream().filter(product -> {
            return product.getBrand() == brand;
        }).toList();
    }

    @Override
    public List<Product> getByCategory(String category) {
        return getAll().stream().filter(product -> {
            return product.getCategory() == category;
        }).toList();
    }

    @Override
    public Boolean addNewProduct(Product product) {
        products.add(product);
        try {
            save();
            return true;
        }catch (IOException e){
            return false;
        }
    }
    private void save() throws IOException{
        try(FileOutputStream stream = new FileOutputStream(fileName)) {
            try(ObjectOutputStream writer = new ObjectOutputStream(stream)){
                writer.writeObject(products);
            }
        }

    }
}
