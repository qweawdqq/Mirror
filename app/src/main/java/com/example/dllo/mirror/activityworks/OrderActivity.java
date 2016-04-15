package com.example.dllo.mirror.activityworks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.H5PayDemoActivity;
import com.alipay.sdk.pay.demo.PayResult;
import com.alipay.sdk.pay.demo.SignUtils;
import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.AddressActivityAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.AllAddressBean;
import com.example.dllo.mirror.bean.OrderBean;
import com.example.dllo.mirror.bean.PayBean;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/11.
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {

    private TextView etAddressee;
    private TextView tvAddress;
    private ImageView ivGlasses;
    private TextView tvGlassesName, tvGlassesContent, tvPrice;
    private Button btnOrder;
    private AllAddressBean bean;
    private Handler handler;
    private int pos;
    private ImageButton btnback;
    private String name, price, goods_id, img;
    private DisplayImageOptions options;  //显示图片的配置
    private String orderId, addrId, goodsName; // 支付 请求参数
    private String payStr;   // 调用支付宝方法使用的str

    private final static int SDK_PAY_FLAG = 1;
    public static String RSA_PRIVATE = "";// 商户私钥，pkcs8格式

    //支付宝之后的回调 判断是否支付成功
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();


                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };


    @Override
    protected int initLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        etAddressee = bindView(R.id.order_tv_addressee);
        tvAddress = bindView(R.id.order_tv_address);
        ivGlasses = bindView(R.id.order_glasses_image);
        tvGlassesName = bindView(R.id.order_glasses_name);
        tvGlassesContent = bindView(R.id.order_glasses_content);
        tvPrice = bindView(R.id.order_glasses_price);
        btnOrder = bindView(R.id.order_btn_order);
        btnback = bindView(R.id.order_btn_back);
    }

    @Override
    protected void initData() {
        setBtnListener();
        addAddress();
        setMyHandler();
        getBundleValue();
    }

    private void setBtnListener() {
        tvAddress.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
    }


    private void setMyHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                pos = msg.arg1;
                AllAddressBean bean = (AllAddressBean) msg.obj;
                etAddressee.setText("收件人: " + bean.getData().getList().get(pos).getUsername() + "\n"
                        + "地址: " + bean.getData().getList().get(pos).getAddr_info() + "\n"
                        + bean.getData().getList().get(pos).getCellphone());

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_tv_address:
                Intent intentToAddress = new Intent(OrderActivity.this, AddressActivity.class);
                startActivityForResult(intentToAddress, 100);

                break;
            case R.id.order_btn_order:
                showDialog();
                break;
            case R.id.order_btn_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        addAddress();

    }

    private void addAddress() {
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
        builder.add("device_type", "3");


        httpNetHelper.getPostDataFromNet(builder, ADDRESS_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {

                Gson gson = new Gson();
                bean = gson.fromJson(s.toString(), AllAddressBean.class);

                String address = NetConnectionStatus.getSharedPrefer(OrderActivity.this, "myAddress");
                for (int i = 0; i < bean.getData().getList().size(); i++) {
                    if (address.equals(bean.getData().getList().get(i).getAddr_id())) {
                        Message msg = Message.obtain();
                        msg.obj = bean;
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                        return;
                    }
                }

            }

            @Override
            public void getFail(String s) {
            }
        });
    }

    private void getBundleValue() {

        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        goods_id = intent.getStringExtra("goods_id");
        img = intent.getStringExtra("img");

        ImageLoader.getInstance().displayImage(img, ivGlasses, options);
        tvGlassesName.setText(name);
        tvPrice.setText(price);

    }


    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.pay_dialog_view, null);
        builder.setView(v);
        RelativeLayout wechat = (RelativeLayout) v.findViewById(R.id.dialog_wechat);
        RelativeLayout alipay = (RelativeLayout) v.findViewById(R.id.dialog_alipay);
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "点击wechat", Toast.LENGTH_SHORT).show();
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder();

            }
        });
        builder.show();
    }


    public void sendOrder() {
        OkHttpNetHelper sendHttpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder sendBuilder = new FormEncodingBuilder();
        // 解析数据
        sendBuilder.add(TOKEN, "fa9d21aa6de9639d76ac1f31d7519326");
        sendBuilder.add(DEVICE_TYPE, "3");
        sendBuilder.add(GOODS_INFO_GOODS_ID, goods_id);
        sendBuilder.add(GOODS_NUM, "1");
        sendBuilder.add(PRICE, price);


        sendHttpNetHelper.getPostDataFromNet(sendBuilder, ORDER_SUB, new NetListener() {
            @Override
            public void getSuccess(String s) {

                Gson gson = new Gson();
                OrderBean orderBean = gson.fromJson(s.toString(), OrderBean.class);
                orderId = orderBean.getData().getOrder_id();
                addrId = orderBean.getData().getAddress().getAddr_id();
                goodsName = orderBean.getData().getGoods().getGoods_name();

                toPay();
            }

            @Override
            public void getFail(String s) {
            }
        });
    }

    public void toPay() {
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add(TOKEN, "fa9d21aa6de9639d76ac1f31d7519326");
        builder.add(DEVICE_TYPE, "3");

        builder.add(ALI_PAY_SUB_ORDER_NO, orderId);
        builder.add(ADDR_ID, addrId);

        builder.add(GOODSNAME, goodsName);


        httpNetHelper.getPostDataFromNet(builder, ALI_PAY_SUB, new NetListener() {
            @Override
            public void getSuccess(String s) {

                Gson gson = new Gson();
                PayBean payBean = gson.fromJson(s.toString(), PayBean.class);
                payStr = payBean.getData().getStr();

                pay();

            }

            @Override
            public void getFail(String s) {
            }
        });
    }


    public void pay() {
        String sign = sign(payStr);

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = payStr + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OrderActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.meituan.com";
        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);

    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
