package com.smallpay.app.ski.rent.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.api.CBSProgressDialogCallBack;
import com.smallpay.app.ski.rent.data.entity.Rent;
import com.smallpay.app.ski.rent.data.entity.RentGoods;
import com.smallpay.app.ski.rent.data.entity.RentItem;
import com.smallpay.app.ski.rent.data.entity.RentMessage;
import com.smallpay.app.ski.rent.data.entity.RentSubItem;
import com.smallpay.app.ski.rent.data.entity.Customer;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.data.remote.ApiEndPoint;
import com.smallpay.app.ski.rent.ui.adapter.RentAdapter;
import com.smallpay.app.ski.rent.ui.base.BaseFragment;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 租借
 *
 * @author ken
 * @date 2018/3/14
 */

public class RentFragment extends BaseFragment implements View.OnClickListener {

    private boolean checkedAll;

    private TextView tvUserName;
    private TextView tvUserIdNum;
    private TextView tvUserPhone;
    private TextView tvUserType;
    private TextView tvTicketType;
    private TextView tvSkiGear;
    private TextView tvCardType;
    private TextView tvCardNum;
    private TextView tvForegift;
    private TextView tvBalance;
    private EditText etCardNum;
    private Button btnSubmit;

    private LinearLayout llRentMessage;

    private RelativeLayout rlCheckAll;
    private ImageView ivCheckAll;
    private Button btnRent;

    private RecyclerView recyclerView;
    private RentAdapter rentAdapter;
    private List<MultiItemEntity> rentItemList = new ArrayList<>();
    private List<RentGoods> rentGoodsList = new ArrayList<>();

    public static RentFragment newInstance() {
        RentFragment fragment = new RentFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.rent_fragment;
    }

    @Override
    public void showNfcCardNo(String cardNo) {
        super.showNfcCardNo(cardNo);
        etCardNum.setText(cardNo);
        etCardNum.setSelection(cardNo.length());
    }

    @Override
    public void initView(View view) {

        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserIdNum = view.findViewById(R.id.tv_user_id_num);
        tvUserPhone = view.findViewById(R.id.tv_user_phone);
        tvUserType = view.findViewById(R.id.tv_user_type);
        tvTicketType = view.findViewById(R.id.tv_ticket_type);
        tvSkiGear = view.findViewById(R.id.tv_ski_gear);
        tvCardType = view.findViewById(R.id.tv_card_type);
        tvCardNum = view.findViewById(R.id.tv_card_num);
        tvForegift = view.findViewById(R.id.tv_foregift);
        tvBalance = view.findViewById(R.id.tv_balance);

        llRentMessage = view.findViewById(R.id.ll_rent_message);

        btnRent = view.findViewById(R.id.rent_btn);
        btnRent.setOnClickListener(this);

        etCardNum = view.findViewById(R.id.et_card_num);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.rent_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rentAdapter = new RentAdapter(rentItemList);
        recyclerView.setAdapter(rentAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        rentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Object object = adapter.getItem(position);
                if (object instanceof RentItem) {
                    RentItem rentItem = (RentItem) object;
                    boolean checked = rentItem.isChecked();
                    rentItem.setChecked(!checked);
                    if (rentItem.getSubItems() != null) {
                        List<RentSubItem> rentSubItemList = rentItem.getSubItems();
                        for (RentSubItem rentSubItem : rentSubItemList) {
                            rentSubItem.setChecked(!checked);
                        }
                    }

                    //选中之后加入租借集合
                    if (rentItem.isChecked()) {
                        if (rentItem.getSubItems() == null && rentItem.getCan_rent() == 1) {
                            rentGoodsList.add(new RentGoods(rentItem.getId(), null));
                        } else {
                            if (rentItem.getSubItems() == null) {
                                return;
                            }
                            List<Integer> subList = new ArrayList<>();
                            for (RentSubItem rentSubItem : rentItem.getSubItems()) {
                                if (rentSubItem.getCan_rent() == 1) {
                                    subList.add(rentSubItem.getId());
                                }
                            }
                            rentGoodsList.add(new RentGoods(rentItem.getId(), subList));
                        }
                    } else {
                        Iterator iterator = rentGoodsList.iterator();
                        while (iterator.hasNext()) {
                            RentGoods rentGoods = (RentGoods) iterator.next();
                            if (rentGoods.getId() == rentItem.getId()) {
                                iterator.remove();
                            }
                        }
                    }

                } else if (object instanceof RentSubItem) {
                    RentSubItem rentSubItem = (RentSubItem) object;
                    boolean checked = rentSubItem.isChecked();
                    rentSubItem.setChecked(!checked);

                    if (rentSubItem.isChecked() && rentSubItem.getCan_rent() == 1) {
                        boolean isFind = false;
                        for (int i : rentSubItem.getGroup_goods_ids()) {
                            for (RentGoods rentGoods : rentGoodsList) {
                                if (i == rentGoods.getId()) {
                                    isFind = true;
                                }
                            }
                        }
                        if (isFind) {
                            for (int i : rentSubItem.getGroup_goods_ids()) {
                                for (RentGoods rentGoods : rentGoodsList) {
                                    if (i == rentGoods.getId()) {
                                        List<Integer> subIds = rentGoods.getSubs();
                                        if (subIds == null) {
                                            subIds = new ArrayList<>();
                                            subIds.add(rentSubItem.getId());
                                            rentGoods.setSubs(subIds);
                                        } else if (!subIds.contains(rentSubItem.getId())) {
                                            subIds.add(rentSubItem.getId());
                                            rentGoods.setSubs(subIds);
                                        }
                                    }
                                }
                            }
                        } else {
                            List<Integer> subList = new ArrayList<>();
                            subList.add(rentSubItem.getId());
                            rentGoodsList.add(new RentGoods(rentSubItem.getGroup_goods_ids()[0], subList));
                        }

                    } else {
                        for (int i : rentSubItem.getGroup_goods_ids()) {
                            Iterator iterator = rentGoodsList.iterator();
                            while (iterator.hasNext()) {
                                RentGoods rentGoods = (RentGoods) iterator.next();
                                if (rentGoods.getId() == i) {
                                    Iterator iterator1 = rentGoods.getSubs().iterator();
                                    while (iterator1.hasNext()) {
                                        int id = (Integer) iterator1.next();
                                        if (id == rentSubItem.getId()) {
                                            iterator1.remove();
                                        }
                                    }
                                    if (rentGoods.getSubs().size() == 0) {
                                        iterator.remove();
                                    }
                                }
                            }
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

        ivCheckAll = view.findViewById(R.id.iv_check_all);
        rlCheckAll = view.findViewById(R.id.rl_check_all);
        rlCheckAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String cardNum = etCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNum)) {
                    ToastUtils.showShort("请填写卡号");
                } else {
                    request(cardNum);
                    KeyboardUtils.hideSoftInput(getActivity());
                }
                break;
            case R.id.rl_check_all:
                checkedAll = !checkedAll;
                ivCheckAll.setImageResource(checkedAll ? R.drawable.icon_right_select : R.drawable.icon_right_normal);
                for (int i = 0, len = rentItemList.size(); i < len; i++) {
                    MultiItemEntity multiItemEntity = rentItemList.get(i);
                    if (multiItemEntity instanceof RentItem) {
                        ((RentItem) multiItemEntity).setChecked(checkedAll);
                    } else if (multiItemEntity instanceof RentSubItem) {
                        ((RentSubItem) multiItemEntity).setChecked(checkedAll);
                    }
                }
                rentAdapter.setNewData(rentItemList);

                if (checkedAll) {
                    for (MultiItemEntity multiItemEntity : rentItemList) {
                        if (multiItemEntity instanceof RentItem) {
                            RentItem rentItem = (RentItem) multiItemEntity;
                            if (rentItem.getCan_rent() == 1) {
                                rentGoodsList.add(new RentGoods(rentItem.getId(), null));
                            }
                        } else if (multiItemEntity instanceof RentSubItem) {
                            RentSubItem rentSubItem = (RentSubItem) multiItemEntity;
                            boolean isFind = false;
                            for (int i : rentSubItem.getGroup_goods_ids()) {
                                for (RentGoods rentGoods : rentGoodsList) {
                                    if (i == rentGoods.getId()) {
                                        isFind = true;
                                    }
                                }
                            }
                            if (isFind) {
                                for (int i : rentSubItem.getGroup_goods_ids()) {
                                    for (RentGoods rentGoods : rentGoodsList) {
                                        if (i == rentGoods.getId()) {
                                            List<Integer> subIds = rentGoods.getSubs();
                                            if (subIds == null) {
                                                subIds = new ArrayList<>();
                                                subIds.add(rentSubItem.getId());
                                                rentGoods.setSubs(subIds);
                                            } else if (!subIds.contains(rentSubItem.getId())) {
                                                subIds.add(rentSubItem.getId());
                                                rentGoods.setSubs(subIds);
                                            }
                                        }
                                    }
                                }
                            } else {
                                List<Integer> subList = new ArrayList<>();
                                subList.add(rentSubItem.getId());
                                rentGoodsList.add(new RentGoods(rentSubItem.getGroup_goods_ids()[0], subList));
                            }
                        }
                    }
                } else {
                    rentGoodsList.clear();
                }

                break;
            case R.id.rent_btn:
                String cardNo = etCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNo) || rentGoodsList.size() == 0) {
                    return;
                }
                Rent rent = new Rent();
                rent.setCard_no(cardNo);
                rent.setRent_ids(rentGoodsList);
                requestRent(rent);
                break;
            default:
        }
    }

    @Override
    public void request(String cardNo) {
        super.request(cardNo);
        requestCardInfo(cardNo);
        requestRentGoods(cardNo);
        requestRentMessage(cardNo);
    }

    private void showCustomer(Customer customer) {

        if (customer == null) {
            tvUserName.setText("");
            tvUserIdNum.setText("");
            tvUserPhone.setText("");
            tvUserType.setText("");
            tvTicketType.setText("");
            tvSkiGear.setText("");
            tvCardType.setText("");
            tvCardNum.setText("");
            tvForegift.setText("");
            tvBalance.setText("");
        } else {
            tvUserName.setText(customer.getCustomer_name());
            tvUserIdNum.setText(customer.getId_no());
            tvUserPhone.setText(customer.getPhone());
            tvUserType.setText(customer.getUser_type_name());
            tvTicketType.setText(customer.getTicket_name());
            tvSkiGear.setText(customer.getIs_own());
            tvCardType.setText(customer.getCard_type_name());
            tvCardNum.setText(customer.getCard_no());
            tvForegift.setText((customer.getPledge() / 100) + "元");
            tvBalance.setText((customer.getAvailable_balance() / 100) + "元");
        }

    }

    private void requestCardInfo(String cardNum) {

        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_CARDS_GET_CUSTOMER_INFO, cardNum + "/"))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            Customer customer = JSON.parseObject(s, Customer.class);
                            showCustomer(customer);
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                showCustomer(null);
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showCustomer(null);
                    }
                });

        disposableList.add(disposable);
    }

    private void requestRentGoods(String cardNum) {
        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);
        String sessionId = JSON.parseObject(userInfo).getString("sessionid");
        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_RENT_FETCH_RENT_GOODS, cardNum + "/"))
                .params("sessionid", sessionId)
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            List<RentItem> rentItems = JSON.parseArray(s, RentItem.class);
                            for (RentItem rentItem : rentItems) {
                                List<RentSubItem> rentSubItems = rentItem.getSub_goods();
                                if (rentSubItems != null && rentSubItems.size() > 0) {
                                    for (RentSubItem rentSubItem : rentSubItems) {
                                        rentItem.addSubItem(rentSubItem);
                                    }
                                }
                            }
                            rentItemList.clear();
                            rentItemList.addAll(rentItems);
                            rentAdapter.setNewData(rentItemList);
                            rentAdapter.expandAll();
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                rentItemList.clear();
                                rentAdapter.setNewData(rentItemList);
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        rentItemList.clear();
                        rentAdapter.setNewData(rentItemList);
                    }
                });
        disposableList.add(disposable);
    }

    private void showRentMessage(List<RentMessage> rentMessageList) {
        llRentMessage.removeAllViews();
        if (rentMessageList == null) {
            return;
        }
        for (RentMessage rentMessage : rentMessageList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.rent_message, null);
            TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
            TextView tvGoodsQuantity = view.findViewById(R.id.tv_goods_quantity);
            TextView tvGoodsStatus = view.findViewById(R.id.tv_goods_status);
            tvGoodsName.setText(rentMessage.getGoods_name());
            tvGoodsQuantity.setText(rentMessage.getQuantity() + "");
            if (rentMessage.getRent_status_name().equals("使用中")) {
                tvGoodsStatus.setTextColor(getResources().getColor(R.color.status_use));
            }
            tvGoodsStatus.setText(rentMessage.getRent_status_name());
            llRentMessage.addView(view);
        }
    }

    private void requestRentMessage(String cardNum) {
        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_RENT_FETCH_RENT_MESSAGE, cardNum + "/"))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            List<RentMessage> rentMessage = JSON.parseArray(s, RentMessage.class);
                            showRentMessage(rentMessage);
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                showRentMessage(null);
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showRentMessage(null);
                    }
                });

        disposableList.add(disposable);

    }

    private void requestRent(final Rent rent) {
        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);
        String sessionId = JSON.parseObject(userInfo).getString("sessionid");
        Disposable disposable = EasyHttp.post(String.format(ApiEndPoint.ENDPOINT_RENT_RENT_GOODS, sessionId))
                .upJson(JSON.toJSONString(rent))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        rentGoodsList.clear();
                        requestCardInfo(rent.getCard_no());
                        requestRentMessage(rent.getCard_no());
                        requestRentGoods(rent.getCard_no());
                    }
                });
        disposableList.add(disposable);
    }

}
