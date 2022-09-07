package com.example.online_shop;

import com.example.online_shop.data.IRepository;
import com.example.online_shop.data.Product;
import com.example.online_shop.data.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    ListView listProducts = new ListView<>();
    private IRepository repository;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new ProductRepository("clothes.data");
        updateListView();
    }
    private void updateListView() {
        List<Product> products = repository.getAll();
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }
}