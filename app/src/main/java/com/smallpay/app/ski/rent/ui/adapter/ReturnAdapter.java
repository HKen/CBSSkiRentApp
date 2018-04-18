package com.smallpay.app.ski.rent.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.data.entity.Goods;
import com.smallpay.app.ski.rent.data.entity.ReturnItem;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class ReturnAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public ReturnAdapter(int layoutResId, @Nullable List<Goods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {

        helper.setText(R.id.tv_goods_name,item.getGoods_name());
        helper.setText(R.id.tv_goods_quantity,item.getQuantity());
        helper.setText(R.id.tv_create_time,item.getCreate_time());

        helper.setImageResource(R.id.iv_check, item.isChecked() ? R.drawable.icon_right_select : R.drawable.icon_right_normal);
        helper.addOnClickListener(R.id.rl_check);
    }
}
