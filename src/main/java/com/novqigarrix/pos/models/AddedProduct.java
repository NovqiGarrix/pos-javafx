package com.novqigarrix.pos.models;

public class AddedProduct {

    private int id;

    private String title;

    private String img;

    private double price;

    private int qty;

    private int index;

    private int stock;

    public AddedProduct() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
            return "AddedProduct {" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", price=" + price +
                    ", qty=" + qty +
                    ", index=" + index +
                    ", stock=" + stock +
                    '}';
    }
}
