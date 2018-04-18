package com.smallpay.app.ski.rent.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.data.entity.RentItem;
import com.smallpay.app.ski.rent.data.entity.RentSubItem;

import java.util.List;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class RentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_RENT_0 = 0;
    public static final int TYPE_RENT_SUB_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RentAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_RENT_0, R.layout.rent_list_item);
        addItemType(TYPE_RENT_SUB_1, R.layout.rent_list_item_sub);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_RENT_0:
                final RentItem rentItem = (RentItem) item;
                holder.setText(R.id.tv_goods_name, rentItem.getGoods_name());
                holder.setText(R.id.tv_goods_quantity, rentItem.getQuantity() + "");
                holder.setImageResource(R.id.iv_check, rentItem.isChecked() ? R.drawable.icon_right_select : R.drawable.icon_right_normal);

                boolean hasSubItem = rentItem.getSubItems() != null && rentItem.getSubItems().size() > 0;

                if (rentItem.getCan_rent() != 1 && !hasSubItem) {
                    holder.setImageResource(R.id.iv_check, R.drawable.icon_right_disabled);
                } else {
                    holder.addOnClickListener(R.id.rl_check);
                }

                if (hasSubItem) {
                    holder.setVisible(R.id.iv_arrow, true);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rentItem.isExpanded()) {
                                collapse(holder.getAdapterPosition());
                            } else {
                                expand(holder.getAdapterPosition());
                            }
                        }
                    });
                } else {
                    holder.setVisible(R.id.iv_arrow, false);
                }
                break;
            case TYPE_RENT_SUB_1:
                RentSubItem rentSubItem = (RentSubItem) item;
                holder.setText(R.id.tv_goods_name, rentSubItem.getGoods_name());
                holder.setText(R.id.tv_goods_quantity, rentSubItem.getQuantity() + "");
                holder.setImageResource(R.id.iv_check, rentSubItem.isChecked() ? R.drawable.icon_right_select : R.drawable.icon_right_normal);
                if (rentSubItem.getCan_rent() != 1) {
                    holder.setImageResource(R.id.iv_check, R.drawable.icon_right_disabled);
                } else {
                    holder.addOnClickListener(R.id.rl_check);
                }
                break;
            default:
        }
    }

}
