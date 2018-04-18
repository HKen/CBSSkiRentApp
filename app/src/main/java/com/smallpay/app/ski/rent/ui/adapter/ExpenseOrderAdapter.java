package com.smallpay.app.ski.rent.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.data.entity.ExpenseOrder;
import com.smallpay.app.ski.rent.data.entity.Goods;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class ExpenseOrderAdapter extends BaseQuickAdapter<ExpenseOrder, BaseViewHolder> {


    public ExpenseOrderAdapter(int layoutResId, @Nullable List<ExpenseOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ExpenseOrder item) {
        helper.setText(R.id.tv_card_no, item.getCard_no());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_state, item.getStatus());
        helper.setText(R.id.tv_total_price, (item.getTotal_amount() / 100) + "元");
        helper.setText(R.id.tv_fixed_price, (item.getReal_price() / 100) + "");
        helper.setText(R.id.tv_timeout_price, (item.getLate_time_price() / 100) + "");
        helper.setText(R.id.tv_start_time, item.getUse_time());
        helper.setText(R.id.tv_end_time, item.getUse_end_time());
        helper.setText(R.id.tv_timeout, item.getLate_time()+"分钟");
        helper.setImageResource(R.id.iv_check, item.isChecked() ? R.drawable.expense_icon_arrow_bottom : R.drawable.expense_icon_arrow_right);
        helper.setGone(R.id.ll_desc, item.isChecked());
        helper.addOnClickListener(R.id.rl_check);
    }

}
