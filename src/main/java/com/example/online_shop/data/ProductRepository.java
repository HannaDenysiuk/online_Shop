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
    public List<Product> searchByName(String name) {
       return getAll().stream().filter(product -> {
            return product.getName().contains(name);
        }).toList();
    }

    @Override
    public List<Product> getByCategory(String category) {
        if(category.equals("All"))
            return getAll().stream().toList();
        else
        return getAll().stream().filter(product -> {
            return product.getCategory().equals(category);
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
