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
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.api.CBSProgressDialogCallBack;
import com.smallpay.app.ski.rent.data.model.Customer;
import com.smallpay.app.ski.rent.data.model.Goods;
import com.smallpay.app.ski.rent.data.model.Return;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.data.remote.ApiEndPoint;
import com.smallpay.app.ski.rent.ui.adapter.ReturnAdapter;
import com.smallpay.app.ski.rent.ui.base.BaseFragment;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 归还
 *
 * @author ken
 * @date 2018/3/14
 */

public class ReturnFragment extends BaseFragment {

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

    private Button btnReturn;

    private RelativeLayout rlCheckAll;
    private ImageView ivCheckAll;

    private RecyclerView recyclerView;
    private ReturnAdapter returnAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private boolean checkedAll;

    private List<Integer> returnIds = new ArrayList<>();

    public static ReturnFragment newInstance() {
        ReturnFragment fragment = new ReturnFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.return_fragment;
    }

    @Override
    public void showNfcCardNo(String cardNo) {
        super.showNfcCardNo(cardNo);
        etCardNum.setText(cardNo);
        etCardNum.setSelection(cardNo.length());
    }

    @Override
    public void request(String cardNo) {
        super.request(cardNo);
        requestCardInfo(cardNo);
        requestRentMessage(cardNo);
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

        btnReturn = view.findViewById(R.id.return_btn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNo = etCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNo) || returnIds.size() == 0) {
                    return;
                }
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < returnIds.size(); i++) {
                    if (i != 0) {
                        sb.append(",");
                    }
                    sb.append(returnIds.get(i));
                }
                Return return1 = new Return();
                return1.setCard_no(cardNo);
                return1.setReturn_ids(sb.toString());
                requestReturn(return1);
            }
        });

        etCardNum = view.findViewById(R.id.et_card_num);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNum = etCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNum)) {
                    ToastUtils.showShort("请填写卡号");
                } else {
                    request(cardNum);
                    KeyboardUtils.hideSoftInput(getActivity());
                }
            }
        });

        recyclerView = view.findViewById(R.id.return_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        returnAdapter = new ReturnAdapter(R.layout.return_list_item, goodsList);
        recyclerView.setAdapter(returnAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        returnAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Goods goods = goodsList.get(position);
                goods.setChecked(!goods.isChecked());
                if (goods.isChecked()) {
                    returnIds.add(goods.getId());
                } else {
                    Iterator iterator = returnIds.iterator();
                    while (iterator.hasNext()) {
                        int id = (Integer) iterator.next();
                        if (goods.getId() == id) {
                            iterator.remove();
                        }
                    }
                }
                returnAdapter.notifyDataSetChanged();
            }
        });


        ivCheckAll = view.findViewById(R.id.iv_check_all);
        rlCheckAll = view.findViewById(R.id.rl_check_all);
        rlCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedAll = !checkedAll;
                ivCheckAll.setImageResource(checkedAll ? R.drawable.icon_right_select : R.drawable.icon_right_normal);
                for (Goods goods : goodsList) {
                    goods.setChecked(checkedAll);
                    if (goods.isChecked()) {
                        returnIds.add(goods.getId());
                    } else {
                        Iterator iterator = returnIds.iterator();
                        while (iterator.hasNext()) {
                            int id = (Integer) iterator.next();
                            if (goods.getId() == id) {
                                iterator.remove();
                            }
                        }
                    }
                }
                returnAdapter.notifyDataSetChanged();
            }
        });

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

    private void showRentMessage(List<Goods> goodsList) {
        llRentMessage.removeAllViews();
        if (goodsList == null) {
            return;
        }
        for (Goods goods : goodsList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.rent_message, null);
            TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
            TextView tvGoodsQuantity = view.findViewById(R.id.tv_goods_quantity);
            TextView tvGoodsStatus = view.findViewById(R.id.tv_goods_status);
            tvGoodsName.setText(goods.getGoods_name());
            tvGoodsQuantity.setText(goods.getQuantity() + "");
            if (goods.getRent_status_name().equals("使用中")) {
                tvGoodsStatus.setTextColor(getResources().getColor(R.color.status_use));
            }
            tvGoodsStatus.setText(goods.getRent_status_name());
            llRentMessage.addView(view);
        }
    }

    private void requestRentMessage(String cardNum) {
        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_RENT_FETCH_RENT_MESSAGE, cardNum + "/"))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            List<Goods> goods = JSON.parseArray(s, Goods.class);
                            showRentMessage(goods);
                            goodsList.clear();
                            for (Goods goods1 : goods) {
                                if (goods1.getRent_status_name().equals("使用中")) {
                                    goodsList.add(goods1);
                                }
                            }
                            returnAdapter.setNewData(goodsList);
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                showRentMessage(null);
                                goodsList.clear();
                                returnAdapter.setNewData(goodsList);
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

    private void requestReturn(final Return return1) {
        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);
        String sessionId = JSON.parseObject(userInfo).getString("sessionid");
        EasyHttp.post(String.format(ApiEndPoint.ENDPOINT_RENT_RETURN_GOODS, sessionId))
                .upJson(JSON.toJSONString(return1))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        returnIds.clear();
                        requestCardInfo(return1.getCard_no());
                        requestRentMessage(return1.getCard_no());
                    }
                });
    }

}
