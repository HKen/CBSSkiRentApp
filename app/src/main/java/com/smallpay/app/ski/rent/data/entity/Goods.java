package com.smallpay.app.ski.rent.data.entity;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class Goods {

    private int id;
    private String goods_name;
    private String goods_type;
    private String rent_status_name;
    private String quantity;
    private int goods_id;
    private String begin_time;
    private String end_time;
    private int rent_amount;
    private int rent_status;
    private int is_child;
    private String update_time;
    private String create_time;
    private String order_code;

    private int amount;
    private String comment;

    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getRent_status_name() {
        return rent_status_name;
    }

    public void setRent_status_name(String rent_status_name) {
        this.rent_status_name = rent_status_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getRent_amount() {
        return rent_amount;
    }

    public void setRent_amount(int rent_amount) {
        this.rent_amount = rent_amount;
    }

    public int getRent_status() {
        return rent_status;
    }

    public void setRent_status(int rent_status) {
        this.rent_status = rent_status;
    }

    public int getIs_child() {
        return is_child;
    }

    public void setIs_child(int is_child) {
        this.is_child = is_child;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
