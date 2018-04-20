package com.smallpay.app.ski.rent.data.model;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class RentGoods {

    private int id;
    private List<Integer> subs;

    public RentGoods(int id, List<Integer> subs) {
        this.id = id;
        this.subs = subs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getSubs() {
        return subs;
    }

    public void setSubs(List<Integer> subs) {
        this.subs = subs;
    }
}
