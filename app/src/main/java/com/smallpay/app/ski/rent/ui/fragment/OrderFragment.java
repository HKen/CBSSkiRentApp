package com.smallpay.app.ski.rent.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.api.CBSProgressDialogCallBack;
import com.smallpay.app.ski.rent.data.model.CompensateOrder;
import com.smallpay.app.ski.rent.data.model.Customer;
import com.smallpay.app.ski.rent.data.model.ExpenseOrder;
import com.smallpay.app.ski.rent.data.model.Order;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.data.remote.ApiEndPoint;
import com.smallpay.app.ski.rent.ui.adapter.CompensateOrderAdapter;
import com.smallpay.app.ski.rent.ui.adapter.ExpenseOrderAdapter;
import com.smallpay.app.ski.rent.ui.base.BaseFragment;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 赔偿
 *
 * @author ken
 * @date 2018/3/14
 */

public class OrderFragment extends BaseFragment {

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
    private LinearLayout llRentMessageTitle;
    private View vRentMessageLine;

    private Button btnCompensate;

    private TextView tvExpenseTotal;

    private RecyclerView rvExpenseOrder;
    private RecyclerView rvCompensateOrder;

    private ExpenseOrderAdapter adapterExpenseOrder;
    private CompensateOrderAdapter adapterCompensateOrder;

    private List<ExpenseOrder> expenseOrderList = new ArrayList<>();
    private List<CompensateOrder> compensateOrderList = new ArrayList<>();


    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.expense_fragment;
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
        requestOrder(cardNo);
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

        llRentMessageTitle = view.findViewById(R.id.ll_rent_message_title);
        llRentMessageTitle.setVisibility(View.GONE);
        vRentMessageLine = view.findViewById(R.id.v_rent_message_line);
        vRentMessageLine.setVisibility(View.GONE);

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

        tvExpenseTotal = view.findViewById(R.id.tv_expense_total);

        rvExpenseOrder = view.findViewById(R.id.rv_expense_order);
        adapterExpenseOrder = new ExpenseOrderAdapter(R.layout.expense_order_list_item, expenseOrderList);
        rvExpenseOrder.setAdapter(adapterExpenseOrder);
        rvExpenseOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterExpenseOrder.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExpenseOrder expenseOrder = expenseOrderList.get(position);
                expenseOrder.setChecked(!expenseOrder.isChecked());
                adapterExpenseOrder.setNewData(expenseOrderList);
            }
        });

        rvCompensateOrder = view.findViewById(R.id.rv_compensate_order);
        adapterCompensateOrder = new CompensateOrderAdapter(R.layout.compensate_order_list_item, compensateOrderList);
        rvCompensateOrder.setAdapter(adapterCompensateOrder);
        rvCompensateOrder.setLayoutManager(new LinearLayoutManager(getContext()));

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

    private void requestOrder(final String cardNum) {
        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);
        String sessionId = JSON.parseObject(userInfo).getString("sessionid");
        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_ORDERS_CARDCURRENT_EXPENSE, cardNum + "/"))
                .params("sessionid", sessionId)
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            Order order = JSON.parseObject(s, Order.class);
                            setExpenseOrderNewData(order.getExpense());
                            setCompensateOrderNewData(order.getCompensate());
                            tvExpenseTotal.setText((order.getTotal() / 100) + "元");
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                setExpenseOrderNewData(null);
                                setCompensateOrderNewData(null);
                                tvExpenseTotal.setText("0元");
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        setExpenseOrderNewData(null);
                        setCompensateOrderNewData(null);
                        tvExpenseTotal.setText("0元");
                    }
                });

        disposableList.add(disposable);
    }

    private void setExpenseOrderNewData(List<ExpenseOrder> list) {
        expenseOrderList.clear();
        if (list != null) {
            expenseOrderList.addAll(list);
        }
        adapterExpenseOrder.setNewData(expenseOrderList);
    }

    private void setCompensateOrderNewData(List<CompensateOrder> list) {
        compensateOrderList.clear();
        if (list != null) {
            compensateOrderList.addAll(list);
        }
        adapterCompensateOrder.setNewData(compensateOrderList);
    }


}
