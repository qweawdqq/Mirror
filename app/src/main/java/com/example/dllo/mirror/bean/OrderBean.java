package com.example.dllo.mirror.bean;

/**
 * Created by dllo on 16/4/15.
 */
public class OrderBean {

    /**
     * result : 1
     * msg :
     * data : {"order_id":"1460685399mCZ","address":{"addr_id":"434","username":"孙健","cellphone":"15998635127","addr_info":"大连市沙河口区数码广场3号软件园10号楼","zip_code":"","city":""},"goods":{"goods_name":"GENTLE MONSTER","goods_num":"","des":"BIG BULLY 飛行員太陽鏡","price":"1300","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg","book_copy":"文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）"},"if_ordain":"1"}
     */

    private String result;
    private String msg;
    /**
     * order_id : 1460685399mCZ
     * address : {"addr_id":"434","username":"孙健","cellphone":"15998635127","addr_info":"大连市沙河口区数码广场3号软件园10号楼","zip_code":"","city":""}
     * goods : {"goods_name":"GENTLE MONSTER","goods_num":"","des":"BIG BULLY 飛行員太陽鏡","price":"1300","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg","book_copy":"文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）"}
     * if_ordain : 1
     */

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
        private String order_id;
        /**
         * addr_id : 434
         * username : 孙健
         * cellphone : 15998635127
         * addr_info : 大连市沙河口区数码广场3号软件园10号楼
         * zip_code :
         * city :
         */

        private AddressBean address;
        /**
         * goods_name : GENTLE MONSTER
         * goods_num :
         * des : BIG BULLY 飛行員太陽鏡
         * price : 1300
         * pic : http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg
         * book_copy : 文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）
         */

        private GoodsBean goods;
        private String if_ordain;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public String getIf_ordain() {
            return if_ordain;
        }

        public void setIf_ordain(String if_ordain) {
            this.if_ordain = if_ordain;
        }

        public static class AddressBean {
            private String addr_id;
            private String username;
            private String cellphone;
            private String addr_info;
            private String zip_code;
            private String city;

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getAddr_info() {
                return addr_info;
            }

            public void setAddr_info(String addr_info) {
                this.addr_info = addr_info;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }

        public static class GoodsBean {
            private String goods_name;
            private String goods_num;
            private String des;
            private String price;
            private String pic;
            private String book_copy;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getBook_copy() {
                return book_copy;
            }

            public void setBook_copy(String book_copy) {
                this.book_copy = book_copy;
            }
        }
    }
}
