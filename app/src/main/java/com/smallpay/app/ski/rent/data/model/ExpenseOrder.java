package com.smallpay.app.ski.rent.data.model;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class ExpenseOrder {

    private String name;
    private String card_no;
    private String use_time;
    private String use_end_time;
    private int late_time;
    private int real_price;
    private int late_time_price;
    private String status;
    private int total_amount;

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(String use_end_time) {
        this.use_end_time = use_end_time;
    }

    public int getLate_time() {
        return late_time;
    }

    public void setLate_time(int late_time) {
        this.late_time = late_time;
    }

    public int getReal_price() {
        return real_price;
    }

    public void setReal_price(int real_price) {
        this.real_price = real_price;
    }

    public int getLate_time_price() {
        return late_time_price;
    }

    public void setLate_time_price(int late_time_price) {
        this.late_time_price = late_time_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
}
