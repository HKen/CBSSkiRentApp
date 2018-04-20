package com.smallpay.app.ski.rent.data.model;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class Compensate {

    private List<CompensateGoods> order_detail_ids;
    private String card_no;
    private int compensate_type;

    public List<CompensateGoods> getOrder_detail_ids() {
        return order_detail_ids;
    }

    public void setOrder_detail_ids(List<CompensateGoods> order_detail_ids) {
        this.order_detail_ids = order_detail_ids;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public int getCompensate_type() {
        return compensate_type;
    }

    public void setCompensate_type(int compensate_type) {
        this.compensate_type = compensate_type;
    }

}
