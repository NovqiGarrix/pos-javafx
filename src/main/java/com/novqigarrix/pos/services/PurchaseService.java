package com.novqigarrix.pos.services;

import com.novqigarrix.pos.DBConnectionUtils;
import com.novqigarrix.pos.models.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseService {

    public void insert(Purchase purchase) throws SQLException {

        String insertSQL = "INSERT INTO purchases (product_id, price, quantity) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnectionUtils.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(insertSQL);
        ) {
            prepStmt.setInt(1, purchase.getProductId());
            prepStmt.setDouble(2, purchase.getPrice());
            prepStmt.setInt(3, purchase.getQuantity());

            prepStmt.execute();
        }

    }

}
