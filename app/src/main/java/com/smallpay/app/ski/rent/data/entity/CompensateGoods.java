package com.smallpay.app.ski.rent.data.entity;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class CompensateGoods {

    private int id;
    private int amount;
    private String desc;

    public CompensateGoods(int id, int amount, String desc) {
        this.id = id;
        this.amount = amount;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
