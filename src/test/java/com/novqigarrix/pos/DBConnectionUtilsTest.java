package com.novqigarrix.pos;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DBConnectionUtilsTest {

    @Test
    void connectionTest() {
        try {
            DBConnectionUtils.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getDataSourceTest() {
        DBConnectionUtils.getDataSource();
    }

}
