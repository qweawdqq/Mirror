package com.example.dllo.mirror.bean;

import java.util.List;

/**
 * Created by dllo on 16/4/14.
 */
public class ShareContentActivityBean {


    /**
     * result : 1
     * msg :
     * data : {"story_title":"152怎樣穿出壹米七大長腿","story_des":"","story_img":"http://7xprhi.com2.z0.glb.qiniucdn.com/e9eb42b696210264b6348dcefdd41fdb6.jpg","story_id":"24","if_original":"1","original_url":"","from":"","story_url":"http://api101.test.mirroreye.cn/index.php/storyweb?id=24&share=1","story_data":{"story_date_type":"2","story_date_url":"http://api101.test.mirroreye.cn/index.php/storyweb?id=2","title":"dfdfdf","subtitle":"dfdfdf","head_img":"http://image.mirroreye.cn/img_88905a9e8d754d18d0b35c77cdb63901844b.png","if_suggest":"2","goods_data":[{"goods_id":"96Psa1455524521","goods_name":"GENTLE MONSTER","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg","goods_price":"1750","goods_des":""},{"goods_id":"28JeX1452078872","goods_name":"SEE CONCEPT","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","goods_price":"450","goods_des":""}]}}
     */

    private String result;
    private String msg;
    /**
     * story_title : 152怎樣穿出壹米七大長腿
     * story_des :
     * story_img : http://7xprhi.com2.z0.glb.qiniucdn.com/e9eb42b696210264b6348dcefdd41fdb6.jpg
     * story_id : 24
     * if_original : 1
     * original_url :
     * from :
     * story_url : http://api101.test.mirroreye.cn/index.php/storyweb?id=24&share=1
     * story_data : {"story_date_type":"2","story_date_url":"http://api101.test.mirroreye.cn/index.php/storyweb?id=2","title":"dfdfdf","subtitle":"dfdfdf","head_img":"http://image.mirroreye.cn/img_88905a9e8d754d18d0b35c77cdb63901844b.png","if_suggest":"2","goods_data":[{"goods_id":"96Psa1455524521","goods_name":"GENTLE MONSTER","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg","goods_price":"1750","goods_des":""},{"goods_id":"28JeX1452078872","goods_name":"SEE CONCEPT","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","goods_price":"450","goods_des":""}]}
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
        private String story_title;
        private String story_des;
        private String story_img;
        private String story_id;
        private String if_original;
        private String original_url;
        private String from;
        private String story_url;
        /**
         * story_date_type : 2
         * story_date_url : http://api101.test.mirroreye.cn/index.php/storyweb?id=2
         * title : dfdfdf
         * subtitle : dfdfdf
         * head_img : http://image.mirroreye.cn/img_88905a9e8d754d18d0b35c77cdb63901844b.png
         * if_suggest : 2
         * goods_data : [{"goods_id":"96Psa1455524521","goods_name":"GENTLE MONSTER","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg","goods_price":"1750","goods_des":""},{"goods_id":"28JeX1452078872","goods_name":"SEE CONCEPT","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","goods_price":"450","goods_des":""}]
         */

        private StoryDataBean story_data;

        public String getStory_title() {
            return story_title;
        }

        public void setStory_title(String story_title) {
            this.story_title = story_title;
        }

        public String getStory_des() {
            return story_des;
        }

        public void setStory_des(String story_des) {
            this.story_des = story_des;
        }

        public String getStory_img() {
            return story_img;
        }

        public void setStory_img(String story_img) {
            this.story_img = story_img;
        }

        public String getStory_id() {
            return story_id;
        }

        public void setStory_id(String story_id) {
            this.story_id = story_id;
        }

        public String getIf_original() {
            return if_original;
        }

        public void setIf_original(String if_original) {
            this.if_original = if_original;
        }

        public String getOriginal_url() {
            return original_url;
        }

        public void setOriginal_url(String original_url) {
            this.original_url = original_url;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getStory_url() {
            return story_url;
        }

        public void setStory_url(String story_url) {
            this.story_url = story_url;
        }

        public StoryDataBean getStory_data() {
            return story_data;
        }

        public void setStory_data(StoryDataBean story_data) {
            this.story_data = story_data;
        }

        public static class StoryDataBean {
            private String story_date_type;
            private String story_date_url;
            private String title;
            private String subtitle;
            private String head_img;
            private String if_suggest;
            /**
             * goods_id : 96Psa1455524521
             * goods_name : GENTLE MONSTER
             * goods_pic : http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg
             * goods_price : 1750
             * goods_des :
             */

            private List<GoodsDataBean> goods_data;

            public String getStory_date_type() {
                return story_date_type;
            }

            public void setStory_date_type(String story_date_type) {
                this.story_date_type = story_date_type;
            }

            public String getStory_date_url() {
                return story_date_url;
            }

            public void setStory_date_url(String story_date_url) {
                this.story_date_url = story_date_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getIf_suggest() {
                return if_suggest;
            }

            public void setIf_suggest(String if_suggest) {
                this.if_suggest = if_suggest;
            }

            public List<GoodsDataBean> getGoods_data() {
                return goods_data;
            }

            public void setGoods_data(List<GoodsDataBean> goods_data) {
                this.goods_data = goods_data;
            }

            public static class GoodsDataBean {
                private String goods_id;
                private String goods_name;
                private String goods_pic;
                private String goods_price;
                private String goods_des;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_pic() {
                    return goods_pic;
                }

                public void setGoods_pic(String goods_pic) {
                    this.goods_pic = goods_pic;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getGoods_des() {
                    return goods_des;
                }

                public void setGoods_des(String goods_des) {
                    this.goods_des = goods_des;
                }
            }
        }
    }
}
