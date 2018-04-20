package com.smallpay.app.ski.rent.data.model;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/21.
 */

public class Order {

    private int total;
    private List<ExpenseOrder> expense;
    private List<CompensateOrder> compensate;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ExpenseOrder> getExpense() {
        return expense;
    }

    public void setExpense(List<ExpenseOrder> expense) {
        this.expense = expense;
    }

    public List<CompensateOrder> getCompensate() {
        return compensate;
    }

    public void setCompensate(List<CompensateOrder> compensate) {
        this.compensate = compensate;
    }

}
