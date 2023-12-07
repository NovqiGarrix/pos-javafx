package com.novqigarrix.pos.services;

import com.novqigarrix.pos.HelloController;
import com.novqigarrix.pos.exceptions.MyException;
import com.novqigarrix.pos.models.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceTest {

    @Test
    void getProductsTest() throws MyException, SQLException {
        ProductService productService = new ProductService();

        var products = productService.getProducts("", 0);
        for (Product product : products) {
            assertNotEquals(product.getId(), 0);
            assertNotNull(product.getTitle());
            assertNotNull(product.getDesc());
            assertNotNull(product.getImg());
            assertNotEquals(product.getPrice(), 0.0);
            assertNotEquals(product.getStock(), 0);
        }
    }

    void setNextPrevPage(HelloController myClass, List<Product> products) {

        try {
            var prevProduct = ProductService.getPreviousProduct(products.get(0).getId());
            System.out.println("Prev Product: " + prevProduct.isPresent());

            if (prevProduct.isPresent()) {
                var prevProductt = prevProduct.get();
                System.out.println("Prev Product: " + prevProductt.getTitle());
                myClass.getLastPrevProductIds().add(prevProductt.getId());
            } else {
                myClass.getLastPrevProductIds().add(products.get(0).getId());
            }

            myClass.getLastNextProductIds().add(products.get(products.size() - 1).getId());
        } catch (MyException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSetNextPrevPage() throws MyException, SQLException {
        ProductService productService = new ProductService();
        HelloController helloController = new HelloController();

        var products = productService.getProducts("", 0);
        setNextPrevPage(helloController, products);

        assertEquals(helloController.getLastNextProductIds().get(0), 63);
        assertEquals(helloController.getLastPrevProductIds().get(0), 52);
    }

}
