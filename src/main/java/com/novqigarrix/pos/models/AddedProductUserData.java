package com.novqigarrix.pos.models;

import com.novqigarrix.pos.HelloController;

public class AddedProductUserData {

    private int productId;

    private HelloController helloController;

    public AddedProductUserData(int productId, HelloController helloController) {
        this.productId = productId;
        this.helloController = helloController;
    }

    public int getProductId() {
        return productId;
    }

    public HelloController getHelloController() {
        return helloController;
    }

}
