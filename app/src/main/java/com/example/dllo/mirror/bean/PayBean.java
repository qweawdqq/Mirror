package com.example.dllo.mirror.bean;

/**
 * Created by dllo on 16/4/15.
 */
public class PayBean {

    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"1460686383VHm\"&subject=\"GENTLE MONSTER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"1300.00\"&body=\"GENTLE MONSTER\"&it_b_pay =\"30m\"&sign=\"VJcrJY23nnDOQmx4Ch6hXBPF21uZT8LXHalnLtLCxmsiTkEoEzPVT%2F%2FZXIXqTVzyE9%2FlH7%2BAVRunjb0exb2CGvZy%2BbO6I%2FDslbbc9%2F1eBW4pDpRuByJm532%2BeL7Die9jQ4xfaeQ8aJqGa9uWJ%2Br2D2Ia6BI27bmE1%2FaUofbf2LI%3D\"&sign_type=\"RSA\""}
     */

    private String msg;
    /**
     * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"&notify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="1460686383VHm"&subject="GENTLE MONSTER"&payment_type="1"&seller_id="2088021758262531"&total_fee="1300.00"&body="GENTLE MONSTER"&it_b_pay ="30m"&sign="VJcrJY23nnDOQmx4Ch6hXBPF21uZT8LXHalnLtLCxmsiTkEoEzPVT%2F%2FZXIXqTVzyE9%2FlH7%2BAVRunjb0exb2CGvZy%2BbO6I%2FDslbbc9%2F1eBW4pDpRuByJm532%2BeL7Die9jQ4xfaeQ8aJqGa9uWJ%2Br2D2Ia6BI27bmE1%2FaUofbf2LI%3D"&sign_type="RSA"
     */

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
}
