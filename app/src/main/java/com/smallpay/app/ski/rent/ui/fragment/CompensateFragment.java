package com.smallpay.app.ski.rent.ui.fragment;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.smallpay.app.ski.rent.data.model.Compensate;
import com.smallpay.app.ski.rent.data.model.CompensateGoods;
import com.smallpay.app.ski.rent.data.model.Customer;
import com.smallpay.app.ski.rent.data.model.Goods;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.data.remote.ApiEndPoint;
import com.smallpay.app.ski.rent.ui.adapter.CompensateAdapter;
import com.smallpay.app.ski.rent.ui.base.BaseFragment;
import com.smallpay.app.ski.rent.util.TPrompt;
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

public class CompensateFragment extends BaseFragment implements CompensateAdapter.EditTextListener {

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

    private Button btnCompensate;

    private RecyclerView recyclerView;
    private CompensateAdapter compensateAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private boolean checkedAll;

    public static CompensateFragment newInstance() {
        CompensateFragment fragment = new CompensateFragment();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.compensate_fragment;
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

        btnCompensate = view.findViewById(R.id.compensate_btn);
        btnCompensate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNo = etCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNo)) {
                    return;
                }

                List<CompensateGoods> compensateGoodsList = new ArrayList<>();

                StringBuffer sb = new StringBuffer();

                for (Goods goods : goodsList) {
                    if (goods.isChecked()) {
                        if (goods.getAmount() == 0) {
                            ToastUtils.showShort("请填写" + goods.getGoods_name() + "的赔偿价格");
                            return;
                        } else if (TextUtils.isEmpty(goods.getComment())) {
                            ToastUtils.showShort("请填写" + goods.getGoods_name() + "的备注");
                            return;
                        }
                        sb.append(goods.getGoods_name() + "--" + goods.getAmount() / 100 + "元--" + goods.getComment() + "\n");
                        compensateGoodsList.add(new CompensateGoods(goods.getId(), goods.getAmount(), goods.getComment()));
                    }
                }

                if (compensateGoodsList.size() == 0) {
                    return;
                }

                final Compensate compensate = new Compensate();
                compensate.setCard_no(cardNo);
                compensate.setCompensate_type(1);
                compensate.setOrder_detail_ids(compensateGoodsList);


                TPrompt.showConfirmDialog(getContext(), "赔偿信息", sb.toString(),
                        "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestCompensate(compensate);
                            }
                        }, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

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
        compensateAdapter = new CompensateAdapter(R.layout.compensate_list_item, goodsList, this

//                new CompensateAdapter.EditTextListener() {
//            @Override
//            public void onTextChanged(Goods item, int etType, String text) {
//                if (TextUtils.isEmpty(text)) {
//                    return;
//                }
//                if (etType == CompensateAdapter.ET_PRICE) {
////                    for (Goods goods : goodsList) {
////                        if (goods.getId() == item.getId()) {
////                            goods.setAmount(Integer.parseInt(text) * 100);
////                            LogUtils.d("id:" + goods.getId() + " amount:" + goods.getAmount());
////                        }
////                    }
//                    Log.d("debug", "id:" + item.getId() + " amount:" + item.getAmount() + " text:" + text);
//                } else if (etType == CompensateAdapter.ET_COMMENT) {
//                    for (Goods goods : goodsList) {
//                        if (goods.getId() == item.getId()) {
//                            goods.setComment(text);
//                            LogUtils.d("id:" + goods.getId() + " comment:" + goods.getComment());
//                        }
//                    }
//                }
//            }
//        }
        );
        recyclerView.setAdapter(compensateAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        compensateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Goods goods = goodsList.get(position);
                goods.setChecked(!goods.isChecked());
                compensateAdapter.setNewData(goodsList);
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

    private void requestRentMessage(final String cardNum) {
        Disposable disposable = EasyHttp.get(String.format(ApiEndPoint.ENDPOINT_RENT_FETCH_RENT_MESSAGE, cardNum + "/"))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            List<Goods> goods = JSON.parseArray(s, Goods.class);
                            showRentMessage(goods);
                            goodsList.clear();
                            goodsList.addAll(goods);
                            compensateAdapter.setNewData(goodsList);
                        } catch (Exception e) {
                            if (JSON.parseObject(s).containsKey("message")) {
                                ToastUtils.showShort(JSON.parseObject(s).getString("message"));
                                showRentMessage(null);
                                goodsList.clear();
                                compensateAdapter.setNewData(goodsList);
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showRentMessage(null);
                        goodsList.clear();
                        compensateAdapter.setNewData(goodsList);
                    }
                });
        disposableList.add(disposable);
    }

    private void requestCompensate(final Compensate compensate) {
        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);
        String sessionId = JSON.parseObject(userInfo).getString("sessionid");
        EasyHttp.post(String.format(ApiEndPoint.ENDPOINT_RENT_COMPENSATE_GOODS, sessionId))
                .upJson(JSON.toJSONString(compensate))
                .execute(new CBSProgressDialogCallBack<String>(getContext()) {
                    @Override
                    public void onSuccess(String o) {
                        super.onSuccess(o);
                        requestCardInfo(compensate.getCard_no());
                        requestRentMessage(compensate.getCard_no());
                    }
                });
    }

    @Override
    public void onPriceTextChanged(Editable s, int position) {
        Goods goods = goodsList.get(position);
        String amount = s.toString();
        goods.setAmount(TextUtils.isEmpty(amount) ? 0 : Integer.parseInt(amount) * 100);
    }

    @Override
    public void onCommentTextChanged(Editable s, int position) {
        Goods goods = goodsList.get(position);
        goods.setComment(s.toString());
    }

}
