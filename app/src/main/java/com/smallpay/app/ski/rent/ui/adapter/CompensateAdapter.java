package com.smallpay.app.ski.rent.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.data.entity.CompensateItem;
import com.smallpay.app.ski.rent.data.entity.Goods;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class CompensateAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public static final int ET_PRICE = 0;
    public static final int ET_COMMENT = 1;

    private EditTextListener listener;

    public CompensateAdapter(int layoutResId, @Nullable List<Goods> data, EditTextListener listener) {
        super(layoutResId, data);
        this.listener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Goods item) {

        LogUtils.d("id = " + item.getId());

        helper.setText(R.id.et_price, (item.getAmount() / 100) + "");
        helper.setText(R.id.et_comment, item.getComment());
        helper.setText(R.id.tv_goods_name, item.getGoods_name());
        helper.setText(R.id.tv_goods_quantity, item.getQuantity());
        helper.setText(R.id.tv_create_time, item.getCreate_time());
        helper.setImageResource(R.id.iv_check, item.isChecked() ? R.drawable.icon_right_select : R.drawable.icon_right_normal);
        helper.addOnClickListener(R.id.rl_check);
        helper.setGone(R.id.et_comment, item.isChecked());
        helper.setGone(R.id.v_comment, item.isChecked());

        ((EditText) helper.getView(R.id.et_price)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
//                    listener.onTextChanged(item, ET_PRICE, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onPriceTextChanged(s, helper.getLayoutPosition());
            }
        });

        ((EditText) helper.getView(R.id.et_comment)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s)) {
//                    listener.onTextChanged(item, ET_COMMENT, s.toString());
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onCommentTextChanged(s, helper.getLayoutPosition());
            }
        });
    }

    public interface EditTextListener {
        void onPriceTextChanged(Editable s, int position);
        void onCommentTextChanged(Editable s, int position);
    }

}
