package com.smallpay.app.ski.rent.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.smallpay.app.ski.rent.ui.adapter.RentAdapter;

/**
 * @author ken
 * @date 2018/3/19.
 */

public class ReturnItem {

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
