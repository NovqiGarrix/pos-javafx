package com.novqigarrix.pos.services;

import com.novqigarrix.pos.DBConnectionUtils;
import com.novqigarrix.pos.GlobalState;
import com.novqigarrix.pos.HelloController;
import com.novqigarrix.pos.controllers.AddedProductController;
import com.novqigarrix.pos.controllers.CardController;
import com.novqigarrix.pos.exceptions.MyException;
import com.novqigarrix.pos.models.AddedProduct;
import com.novqigarrix.pos.models.Product;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.novqigarrix.pos.Utils.handleSqlQueryException;

public class ProductService {

    public ProductService() {
    }

    // Get all products from database
    public List<Product> getProducts(String keyword, int cursor) throws MyException, SQLException {

        var products = new ArrayList<Product>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            conn = DBConnectionUtils.getConnection();
            statement = conn.prepareStatement("""
                                SELECT * FROM products WHERE name LIKE ? AND id > ? LIMIT 12
                    """);

            statement.setString(1, "%" + keyword + "%");
            statement.setInt(2, cursor);
            rs = statement.executeQuery();

            while (rs.next()) {
                var p = Product.fromResultSet(rs);
                products.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new MyException("Failed to get products");
        } finally {
            handleSqlQueryException(rs, statement, conn);
        }

        return products;

    }

    // Reduce product stock
    public void reduceProductStock(int productId, int qty) throws MyException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            conn = DBConnectionUtils.getConnection();
            statement = conn.prepareStatement("""
                                UPDATE products SET stock = stock - ? WHERE id = ?
                    """);

            statement.setInt(1, qty);
            statement.setInt(2, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new MyException("Failed to update product with id: " + productId);
        } finally {
            handleSqlQueryException(rs, statement, conn);
        }

    }

    // Get product by id
    public static Optional<Product> getProductById(int productId) throws MyException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            conn = DBConnectionUtils.getConnection();
            statement = conn.prepareStatement("""
                                SELECT * FROM products WHERE id = ?
                    """);

            statement.setInt(1, productId);
            rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Product p = Product.fromResultSet(rs);
            return Optional.of(p);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("Failed to get product with id: " + productId);
        } finally {
            handleSqlQueryException(rs, statement, conn);
        }

    }

    // Get previous product by id
    public static Optional<Product> getPreviousProduct(int productId) throws MyException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            conn = DBConnectionUtils.getConnection();
            statement = conn.prepareStatement("""
                                SELECT * FROM products WHERE id < ? LIMIT 1
                    """);

            statement.setInt(1, productId);
            rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Product p = Product.fromResultSet(rs);
            return Optional.of(p);

        } catch (SQLException e) {
            throw new MyException("Failed to get product previous product with id: " + productId);
        } finally {
            handleSqlQueryException(rs, statement, conn);
        }

    }

    // Get next product by id
    public static Optional<Product> getNextProduct(int productId) throws MyException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            conn = DBConnectionUtils.getConnection();
            statement = conn.prepareStatement("""
                                SELECT * FROM products WHERE id > ? LIMIT 1
                    """);

            statement.setInt(1, productId);
            rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Product p = Product.fromResultSet(rs);
            return Optional.of(p);

        } catch (SQLException e) {
            throw new MyException("Failed to get product previous product with id: " + productId);
        } finally {
            handleSqlQueryException(rs, statement, conn);
        }

    }

    // Handle when each product is clicked
    private void onCardClick(HelloController myClass, AddedProductService addedProductService, Product product) throws IOException {

        VBox addedProductContainer = myClass.getAddedProductContainer();
        var scrollPane = myClass.getAddedProductScrollPane();

//        Check if product is already added
        var addedProduct = GlobalState.addedProductList.containsKey(product.getId());

        if (addedProduct) {
            var addedProd = GlobalState.addedProductList.get(product.getId());

            if (addedProd.getQty() + 1 <= addedProd.getStock()) {
                addedProd.setQty(addedProd.getQty() + 1);
            }

//            Find the added product component
            var addedProductComponent = addedProductContainer.getChildren().get(addedProd.getIndex());
            AddedProductController addedProductController = (AddedProductController) addedProductComponent.getProperties().get("controller");

//            Update UI
            addedProductController.setAddedProduct(addedProd, false);

//            Scroll to the updated added product
            double totalHeight = 0;
            for (Node child : addedProductContainer.getChildren()) {
                totalHeight += child.getBoundsInParent().getHeight();
                if (child == addedProductComponent) {
                    break;
                }
            }
            double targetVvalue = totalHeight / addedProductContainer.getHeight();
            scrollToComponent(scrollPane, targetVvalue);
        } else {
            var addedProd = new AddedProduct();
            addedProd.setId(product.getId());
            addedProd.setTitle(product.getTitle());
            addedProd.setPrice(product.getPrice());
            addedProd.setImg(product.getImg());
            addedProd.setQty(1);
            addedProd.setIndex(GlobalState.addedProductList.size());
            addedProd.setStock(product.getStock());

            var addedComponent = addedProductService.loadAndSetAddedProduct(myClass, addedProd);
            addedProductService.addAddedProduct(addedProductContainer, addedComponent);
            GlobalState.addedProductList.put(product.getId(), addedProd);

//            Scroll to bottom
            scrollToComponent(scrollPane, 1);

        }

        myClass.calculateTotal();

    }

    // Scroll to current added product
    private static void scrollToComponent(ScrollPane scrollPane, double vValue) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), new KeyValue(scrollPane.vvalueProperty(), vValue))
        );
        timeline.play();
    }

    // Load products to the container
    public void loadProducts(HelloController myClass, int cursor) {

        try {

            final int[] row = {1};
            final int[] column = {0};

            var products = getProducts(myClass.getSearchField().getText(), cursor);

            for (Product product : products) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(myClass.getClass().getResource("components/card.fxml"));

                HBox cardComponentContainer = fxmlLoader.load();

                VBox cardComponent = (VBox) cardComponentContainer.getChildren().get(0);
                CardController cardController = fxmlLoader.getController();
                cardController.setProduct(product);

                if (product.getStock() > 0) {
                    // When card is clicked
                    EventHandler<MouseEvent> eventHandler = mouseEvent -> {
                        try {
                            onCardClick(myClass, myClass.getAddedProductService(), product);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    };

                    cardComponent.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                }

                // We have to use Platform.runLater
                // to update the UI
                Platform.runLater(() -> {
                    if (column[0] == 4) {
                        column[0] = 0;
                        ++row[0];
                    }

                    myClass.getProductListContainer().add(cardComponent, column[0]++, row[0]);
                    GridPane.setMargin(cardComponent, new Insets(8, 16, 8, 0));
                });
            }

            myClass.getLastPrevProductIds().add(products.get(0).getId() - 1);
            myClass.getLastNextProductIds().add(products.get(products.size() - 1).getId());

            // Check if there is next page
            boolean thereIsNextPage = getNextProduct(products.getLast().getId()).isPresent();

            myClass.setThereIsNextPage(thereIsNextPage);
            myClass.setEnableOrDisablePaginationBtn();

        } catch (MyException | SQLException | IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException ignored) {
            System.out.println("Out of bond");
        }
    }

}
