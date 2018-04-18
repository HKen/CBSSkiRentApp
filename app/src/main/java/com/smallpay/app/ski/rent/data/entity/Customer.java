package com.smallpay.app.ski.rent.data.entity;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class Customer {

    private String card_no;
    private int card_type;
    private String card_type_name;
    private int user_type;
    private String user_type_name;
    private String pay_channel;
    private String customer_name;
    private String id_type;
    private String id_no;
    private String phone;
    private String is_own;
    private String ticket_name;
    private int available_balance;
    private int pledge;
    private int consume;
    private int balance;

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }

    public String getCard_type_name() {
        return card_type_name;
    }

    public void setCard_type_name(String card_type_name) {
        this.card_type_name = card_type_name;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUser_type_name() {
        return user_type_name;
    }

    public void setUser_type_name(String user_type_name) {
        this.user_type_name = user_type_name;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIs_own() {
        return is_own;
    }

    public void setIs_own(String is_own) {
        this.is_own = is_own;
    }

    public String getTicket_name() {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public int getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(int available_balance) {
        this.available_balance = available_balance;
    }

    public int getPledge() {
        return pledge;
    }

    public void setPledge(int pledge) {
        this.pledge = pledge;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
