package com.smallpay.app.ski.rent.data.model;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class Rent {

    private List<RentGoods> rent_ids;
    private String card_no;

    public List<RentGoods> getRent_ids() {
        return rent_ids;
    }

    public void setRent_ids(List<RentGoods> rent_ids) {
        this.rent_ids = rent_ids;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

}
