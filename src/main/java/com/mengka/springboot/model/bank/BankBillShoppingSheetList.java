package com.mengka.springboot.model.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BankBillShoppingSheetList {
    @JsonProperty("total_size")
    private Integer totalSize;
    private Integer size;
    @JsonProperty("shopping_sheets")
    private List<BankBillShoppingSheet> shoppingSheets;

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<BankBillShoppingSheet> getShoppingSheets() {
        return shoppingSheets;
    }

    public void setShoppingSheets(List<BankBillShoppingSheet> shoppingSheets) {
        this.shoppingSheets = shoppingSheets;
    }
}
