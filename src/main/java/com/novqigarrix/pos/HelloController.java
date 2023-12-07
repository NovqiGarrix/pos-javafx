package com.novqigarrix.pos;

import com.novqigarrix.pos.exceptions.MyException;
import com.novqigarrix.pos.models.Purchase;
import com.novqigarrix.pos.services.AddedProductService;
import com.novqigarrix.pos.services.ProductService;
import com.novqigarrix.pos.services.PurchaseService;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.reactfx.EventStreams;

import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ScrollPane addedProductScrollPane;

    @FXML
    private VBox addedProductContainer;

    @FXML
    private GridPane productListContainer;

    @FXML
    private TextField searchField;

    @FXML
    private TextField discountTextField;

    @FXML
    private Label subtotal;

    @FXML
    private Label totalPrice;

    @FXML
    private Button paymentBtn;

    @FXML
    private Label discountErrorLabel;

    @FXML
    private Button nextPageBtn;

    @FXML
    private Button prevPageBtn;

    @FXML
    private ImageView loadingIconForPrevPage;

    @FXML
    private ImageView loadingIconForNextPage;

    @FXML
    private ImageView loadingIconForPaymentBtn;

    private boolean thereIsNextPage;

    private final List<Integer> lastNextProductIds = new ArrayList<>();

    private final List<Integer> lastPrevProductIds = new ArrayList<>();

    private int currentIndexPage = 0;

    public boolean canSearchRun = false;

    private final ProductService productService = new ProductService();

    private final PurchaseService purchaseService = new PurchaseService();

    private final AddedProductService addedProductService = new AddedProductService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set HTTP Agent to Chrome
        // to avoid 403 error
        // when loading images
        System.setProperty("http.agent", "Chrome");

        // Create loading icon
        setupLoadingIcon(loadingIconForPrevPage);
        setupLoadingIcon(loadingIconForNextPage);
        setupLoadingIcon(loadingIconForPaymentBtn);

        // By default, previous page button is disabled
        prevPageBtn.setDisable(true);

        // Set cursor when hover
        setCursorWhenHover(prevPageBtn);
        setCursorWhenHover(nextPageBtn);
        setCursorWhenHover(paymentBtn);

        // By default, hide discount error label
        discountErrorLabel.setOpacity(0);

        // Set discount text field to not autofocus
        discountTextField.setFocusTraversable(false);

        // Update total price when discount text field is changed
        discountTextField.textProperty().addListener((observableValue, s, t1) -> calculateTotal());

        // Load products if the search field is changed
        EventStreams.valuesOf(searchField.textProperty())
                .successionEnds(Duration.ofMillis(100)) // debounce time
                .reduceSuccessions((a, b) -> b, Duration.ofMillis(1000)) // throttle time
                .subscribe(searchText -> { // When the search field is changed

                    // Avoid search when the app is started
                    if (searchText.isEmpty() && !canSearchRun) {
                        canSearchRun = true;
                        return;
                    }

                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() {
                            // Load products with the search text
                            Platform.runLater(() -> productListContainer.getChildren().clear());

                            loadProducts();
                            return null;
                        }
                    };

                    new Thread(task).start();
                });


        // On previous page button click
        prevPageBtn.setOnMouseClicked((mouseEvent -> {

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {

                    try {
                        // Get last previous product id
                        int lastPrevProductId = lastPrevProductIds.get(currentIndexPage - 1);

                        // Load products with the last previous product id
                        var prevPageBtnText = prevPageBtn.getText();

                        // We have to use Platform.runLater
                        // to update the UI
                        Platform.runLater(() -> {
                            prevPageBtn.setText("");
                            prevPageBtn.setDisable(true);
                            loadingIconForPrevPage.setVisible(true);
                            productListContainer.getChildren().clear();
                        });

                        currentIndexPage--;
                        loadProducts(lastPrevProductId);

                        // We have to use Platform.runLater
                        // to update the UI
                        Platform.runLater(() -> {
                            // Hide the loading icon
                            prevPageBtn.setText(prevPageBtnText);
                            loadingIconForPrevPage.setVisible(false);
                        });
                    } catch (IndexOutOfBoundsException e) {
                        // do nothing
                    }

                    return null;
                }
            };

            new Thread(task).start();

        }));

        // On next page button click
        nextPageBtn.setOnMouseClicked((mouseEvent -> {

            // Get last next product id
            int currentLastProductId = lastNextProductIds.get(currentIndexPage);

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    // Load products with the last next product id
                    var nextPageBtnText = nextPageBtn.getText();

                    // We have to use Platform.runLater
                    // to update the UI
                    Platform.runLater(() -> {
                        nextPageBtn.setDisable(true);
                        nextPageBtn.setText("");
                        loadingIconForNextPage.setVisible(true);
                        productListContainer.getChildren().clear();
                    });

                    currentIndexPage++;
                    loadProducts(currentLastProductId);

                    // We have to use Platform.runLater
                    // to update the UI
                    Platform.runLater(() -> {
                        nextPageBtn.setText(nextPageBtnText);
                        loadingIconForNextPage.setVisible(false);
                    });

                    return null;
                }
            };

            new Thread(task).start();

        }));

        // On payment button click
        paymentBtn.setOnMouseClicked((mouseEvent) -> {

            // Parse total price from Label component to Long
            long totalPrice = Long.parseLong(this.totalPrice.getText().replace("Rp", "").replaceAll("\\.", ""));

            // If total price is 0, do nothing
            if (totalPrice <= 0) {
                return;
            }

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    // Show the loading icon
                    Platform.runLater(() -> {
                        loadingIconForPaymentBtn.setVisible(true);
                        paymentBtn.setText("");
                    });

                    // Reduce stock
                    GlobalState.addedProductList.forEach((id, addedProduct) -> {
                        try {
                            // Call reduceProductStock method
                            // from ProductService
                            // to reduce the product stock
                            productService.reduceProductStock(addedProduct.getId(), addedProduct.getQty());

                            // Insert the product to purchases table
                            Purchase purchaseProduct = new Purchase();
                            purchaseProduct.setProductId(addedProduct.getId());
                            purchaseProduct.setQuantity(addedProduct.getQty());
                            purchaseProduct.setPrice(addedProduct.getPrice());

                            purchaseService.insert(purchaseProduct);
                        } catch (MyException | SQLException e) {
                            System.out.println("Err reduce the stock: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });

                    // Re-reload products
                    loadProducts();

                    // Clear added product list
                    GlobalState.addedProductList.clear();

                    // Update the UI
                    Platform.runLater(() -> {

                        paymentBtn.setText("Continue Payment");
                        loadingIconForPaymentBtn.setVisible(false);

                        // Clear added product component
                        addedProductContainer.getChildren().clear();

                        // Reset subtotal and total price
                        calculateTotal();

                    });
                    return null;
                }
            };

            new Thread(task).start();

        });

    }

    public void setupLoadingIcon(ImageView loadingIcon) {
        loadingIcon.setVisible(false);

        loadingIcon.setOpacity(0.6);
        loadingIcon.setFitWidth(20);
        loadingIcon.setFitHeight(20);
//        loadingIcon.setImage(new Image(String.valueOf(getClass().getResource("assets/loading.png"))));

        // Create a RotateTransition
        RotateTransition rotateTransition = new RotateTransition();

        // Set the duration of the rotation
        rotateTransition.setDuration(javafx.util.Duration.millis(800));

        // Set the node to rotate
        rotateTransition.setNode(loadingIcon);

        // Set the angle to rotate from and to
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);

        // Set the rotation to be indefinite
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);

        // Start the rotation
        rotateTransition.play();
    }

    public void setEnableOrDisablePaginationBtn() {
        // Disable previous page button if current index page is 0
        // that means the current page is the first page
        prevPageBtn.setDisable(currentIndexPage == 0);

        // Disable next page button if there is no next page
        nextPageBtn.setDisable(!thereIsNextPage);
    }

    public void loadProducts() {
        productService.loadProducts(this, 0);
    }

    public void loadProducts(int cursor) {
        productService.loadProducts(this, cursor);
    }

    public void setCursorWhenHover(Button component) {
        component.setOnMouseEntered(mouseEvent -> component.setCursor(Cursor.HAND));
        component.setOnMouseExited(mouseEvent -> component.setCursor(Cursor.DEFAULT));
    }

    public void calculateTotal() {
        // Calculate subtotal and total price
        double subtotal = GlobalState.getAddedProductListAsList().stream().mapToDouble(ap -> ap.getPrice() * ap.getQty()).sum();

        this.subtotal.setText("Rp" + Utils.getPriceFormatter().format(subtotal));

        var discountText = discountTextField.getText();
        try {

            if (discountErrorLabel.getOpacity() == 1) {
                discountErrorLabel.setOpacity(0);
            }

            double discount = discountText.isEmpty() ? 0 : Double.parseDouble(discountText);

            // Calculate the total price
            var totalPrice = subtotal - (subtotal * (discount / 100));
            this.totalPrice.setText("Rp" + Utils.getPriceFormatter().format(totalPrice));
        } catch (NumberFormatException e) {
            if (discountText.contains("%")) {
                discountErrorLabel.setText("Hapus tanda %");
            } else {
                discountErrorLabel.setText("Discount tidak valid");
            }

            discountErrorLabel.setOpacity(1);
        }
    }

    public void setThereIsNextPage(boolean thereIsNextPage) {
        this.thereIsNextPage = thereIsNextPage;
    }

    public ScrollPane getAddedProductScrollPane() {
        return addedProductScrollPane;
    }

    public VBox getAddedProductContainer() {
        return addedProductContainer;
    }

    public GridPane getProductListContainer() {
        return productListContainer;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public List<Integer> getLastNextProductIds() {
        return lastNextProductIds;
    }

    public List<Integer> getLastPrevProductIds() {
        return lastPrevProductIds;
    }

    public AddedProductService getAddedProductService() {
        return addedProductService;
    }
}