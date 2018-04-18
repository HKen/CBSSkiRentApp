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
import com.smallpay.app.ski.rent.data.entity.CompensateOrder;
import com.smallpay.app.ski.rent.data.entity.Goods;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class CompensateOrderAdapter extends BaseQuickAdapter<CompensateOrder, BaseViewHolder> {

    public CompensateOrderAdapter(int layoutResId, @Nullable List<CompensateOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CompensateOrder item) {
        helper.setText(R.id.tv_card_no, item.getCard_no());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_price, (item.getAmount() / 100) + "");
        helper.setText(R.id.tv_desc, item.getDescription());
    }

}
