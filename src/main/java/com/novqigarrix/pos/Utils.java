package com.novqigarrix.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static NumberFormat getPriceFormatter() {
        return NumberFormat.getNumberInstance(Locale.of("id", "ID"));
    }

    public static void handleSqlQueryException(ResultSet rs, PreparedStatement statement, Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (conn != null) {
            conn.close();
        }
    }

}
