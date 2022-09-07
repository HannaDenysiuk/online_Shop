package com.example.online_shop.data;

import java.util.List;

public interface IRepository {
    List<Product> getAll();
    List<Product> getByBrand(String brand);
    List<Product> getByCategory(String category);
    Boolean addNewProduct(Product product);
}
