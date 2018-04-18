package com.smallpay.app.ski.rent.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.smallpay.app.ski.rent.ui.adapter.RentAdapter;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class RentItem extends AbstractExpandableItem<RentSubItem> implements MultiItemEntity {

    private int id;
    private int can_rent;
    private int[] group_goods_ids;
    private List<RentSubItem> sub_goods;
    private int merchant_id;
    private int category_id;
    private String category_name;
    private int goods_type;
    private String goods_name;
    private String goods_desc;
    private int pledge_amount;
    private int rental_fee;
    private int price_unit;
    private int price;
    private int quantity;
    private int charged_type;
    private int ticket_from;
    private int is_enable;
    private String update_time;
    private String create_time;

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return RentAdapter.TYPE_RENT_0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCan_rent() {
        return can_rent;
    }

    public void setCan_rent(int can_rent) {
        this.can_rent = can_rent;
    }

    public int[] getGroup_goods_ids() {
        return group_goods_ids;
    }

    public void setGroup_goods_ids(int[] group_goods_ids) {
        this.group_goods_ids = group_goods_ids;
    }

    public List<RentSubItem> getSub_goods() {
        return sub_goods;
    }

    public void setSub_goods(List<RentSubItem> sub_goods) {
        this.sub_goods = sub_goods;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public int getPledge_amount() {
        return pledge_amount;
    }

    public void setPledge_amount(int pledge_amount) {
        this.pledge_amount = pledge_amount;
    }

    public int getRental_fee() {
        return rental_fee;
    }

    public void setRental_fee(int rental_fee) {
        this.rental_fee = rental_fee;
    }

    public int getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(int price_unit) {
        this.price_unit = price_unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCharged_type() {
        return charged_type;
    }

    public void setCharged_type(int charged_type) {
        this.charged_type = charged_type;
    }

    public int getTicket_from() {
        return ticket_from;
    }

    public void setTicket_from(int ticket_from) {
        this.ticket_from = ticket_from;
    }

    public int getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(int is_enable) {
        this.is_enable = is_enable;
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
}
