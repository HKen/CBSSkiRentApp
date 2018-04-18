package com.smallpay.app.ski.rent.ui.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.ui.base.BaseFragment;
import com.smallpay.app.ski.rent.ui.fragment.CompensateFragment;
import com.smallpay.app.ski.rent.ui.fragment.OrderFragment;
import com.smallpay.app.ski.rent.ui.fragment.RentFragment;
import com.smallpay.app.ski.rent.ui.fragment.ReturnFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements View.OnClickListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private int current_index = FIRST;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private RelativeLayout rentTab;
    private RelativeLayout returnTab;
    private RelativeLayout compensateTab;
    private RelativeLayout expenseTab;

    private ImageView rentArrow;
    private ImageView returnArrow;
    private ImageView compensateArrow;
    private ImageView expenseArrow;

    private TextView tvUserName;

    private NfcAdapter nfcAdapter;
    private PendingIntent mPendingIntent;

    @Override
    public void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        processIntent(tag);
    }

    /**
     * 启动Activity，界面可见时
     */
    @Override
    protected void onStart() {
        super.onStart();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //一旦截获NFC消息，就会通过PendingIntent调用窗口
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
    }

    /**
     * 获得焦点，按钮可以点击
     */
    @Override
    public void onResume() {
        super.onResume();
        //设置处理优于所有其他NFC的处理
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        }
    }

    /**
     * 暂停Activity，界面获取焦点，按钮可以点击
     */
    @Override
    public void onPause() {
        super.onPause();
        //恢复默认状态
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tv_username);

        SupportFragment firstFragment = findFragment(RentFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = RentFragment.newInstance();
            mFragments[SECOND] = ReturnFragment.newInstance();
            mFragments[THIRD] = CompensateFragment.newInstance();
            mFragments[FOURTH] = OrderFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(ReturnFragment.class);
            mFragments[THIRD] = findFragment(CompensateFragment.class);
            mFragments[FOURTH] = findFragment(CompensateFragment.class);
        }

        initView();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            ToastUtils.showShort("设备不支持NFC！");
            return;
        }
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            ToastUtils.showShort("请在系统设置中先启用NFC功能！");
            return;
        }

    }

    private void initView() {

        String userInfo = SPUtils.getInstance().getString(Constants.SHARE_KEY_LOGIN_SUCCESS);

        if (!TextUtils.isEmpty(userInfo)) {
            String username = JSON.parseObject(userInfo).getString("username");
            tvUserName.setText(username);
        }

        rentTab = (RelativeLayout) findViewById(R.id.rl_rent_tab);
        returnTab = (RelativeLayout) findViewById(R.id.rl_return_tab);
        compensateTab = (RelativeLayout) findViewById(R.id.rl_compensate_tab);
        expenseTab = (RelativeLayout) findViewById(R.id.rl_expense_tab);

        rentArrow = (ImageView) findViewById(R.id.iv_rent_arrow);
        returnArrow = (ImageView) findViewById(R.id.iv_return_arrow);
        compensateArrow = (ImageView) findViewById(R.id.iv_compensate_arrow);
        expenseArrow = (ImageView) findViewById(R.id.iv_expense_arrow);

        rentTab.setOnClickListener(this);
        returnTab.setOnClickListener(this);
        compensateTab.setOnClickListener(this);
        expenseTab.setOnClickListener(this);

    }


    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private void processIntent(Tag tagFromIntent) {


        //读取TAG
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            //metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
            //    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";

            if (mfc.authenticateSectorWithKeyA(0,
                    MifareClassic.KEY_DEFAULT) || mfc.authenticateSectorWithKeyA(0,
                    MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY)) {
                int bCount, bIndex;
                // 读取扇区中的块
                bCount = mfc.getBlockCountInSector(0);
                bIndex = mfc.sectorToBlock(0);

                byte[] data = mfc.readBlock(1);

                metaInfo = hex2String(bytes2HexString(subBytes(data, 8, 8)));

            }

//            et_nfc.setText(metaInfo);

//            LogUtils.e("nfc = " + metaInfo);

//            ToastUtils.showShort(metaInfo);


            ((BaseFragment)mFragments[current_index]).showNfcCardNo(metaInfo);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }


    /*
     * 字节数组转字符串
     */
    public static String bytes2String(byte[] b) throws Exception {
        String r = new String(b, "UTF-8");
        return r;
    }


    /*
     * 16进制字符串转字符串
     */
    public static String hex2String(String hex) throws Exception {
        String r = bytes2String(hexString2Bytes(hex));
        return r;
    }


    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /*
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }

    }

    /**
     * @param src
     * @param begin
     * @param count
     * @return 在字节数组中截取指定长度数组
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_rent_tab:
                rentArrow.setVisibility(View.VISIBLE);
                rentTab.setBackgroundColor(getResources().getColor(R.color.tab_background_select));
                returnArrow.setVisibility(View.INVISIBLE);
                returnTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                compensateArrow.setVisibility(View.INVISIBLE);
                compensateTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                expenseArrow.setVisibility(View.INVISIBLE);
                expenseTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                if (current_index == FIRST) {
                    showHideFragment(mFragments[FIRST]);
                } else {
                    showHideFragment(mFragments[FIRST], mFragments[current_index]);
                }
                current_index = FIRST;
                break;
            case R.id.rl_return_tab:
                returnArrow.setVisibility(View.VISIBLE);
                returnTab.setBackgroundColor(getResources().getColor(R.color.tab_background_select));
                rentArrow.setVisibility(View.INVISIBLE);
                rentTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                compensateArrow.setVisibility(View.INVISIBLE);
                compensateTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                expenseArrow.setVisibility(View.INVISIBLE);
                expenseTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                showHideFragment(mFragments[SECOND], mFragments[current_index]);
                current_index = SECOND;
                break;
            case R.id.rl_compensate_tab:
                compensateArrow.setVisibility(View.VISIBLE);
                compensateTab.setBackgroundColor(getResources().getColor(R.color.tab_background_select));
                rentArrow.setVisibility(View.INVISIBLE);
                rentTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                returnArrow.setVisibility(View.INVISIBLE);
                returnTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                expenseArrow.setVisibility(View.INVISIBLE);
                expenseTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                showHideFragment(mFragments[THIRD], mFragments[current_index]);
                current_index = THIRD;
                break;
            case R.id.rl_expense_tab:
                expenseArrow.setVisibility(View.VISIBLE);
                expenseTab.setBackgroundColor(getResources().getColor(R.color.tab_background_select));
                compensateArrow.setVisibility(View.INVISIBLE);
                compensateTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                rentArrow.setVisibility(View.INVISIBLE);
                rentTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                returnArrow.setVisibility(View.INVISIBLE);
                returnTab.setBackgroundColor(getResources().getColor(R.color.tab_background_normal));
                showHideFragment(mFragments[FOURTH], mFragments[current_index]);
                current_index = FOURTH;
                break;
            default:
        }

    }

}
