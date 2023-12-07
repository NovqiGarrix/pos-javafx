package com.novqigarrix.pos;

import com.novqigarrix.pos.models.AddedProduct;

import java.util.HashMap;
import java.util.List;

public class GlobalState {

    public static final HashMap<Integer, AddedProduct> addedProductList = new HashMap<>();

    public static List<AddedProduct> getAddedProductListAsList() {
        return List.copyOf(addedProductList.values());
    }

}
