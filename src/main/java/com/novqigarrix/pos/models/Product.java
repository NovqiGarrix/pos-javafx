package com.novqigarrix.pos.models;

import com.novqigarrix.pos.exceptions.MyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    private final int id;

    private final String title;

    private final String desc;

    private final String img;

    private final double price;

    private final int stock;

    public Product(int id, String title, String desc, String img, double price, int stock) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.stock = stock;
    }

    // This method is used in ProductService.java
    // to convert ResultSet to Product object
    public static Product fromResultSet(ResultSet rs) throws MyException {
        try {
            var productId = rs.getInt("id");
            var productName = rs.getString("name");
            var productPrice = rs.getDouble("price");
            var productImg = rs.getString("img");
            var productDesc = rs.getString("description");
            var productStock = rs.getInt("stock");

            return new Product(productId, productName, productDesc, productImg, productPrice, productStock);
        } catch (SQLException e) {
            System.out.println("Err: " + e.getMessage());
            throw new MyException(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
