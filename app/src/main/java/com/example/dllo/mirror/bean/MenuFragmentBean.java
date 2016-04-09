package com.example.dllo.mirror.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class MenuFragmentBean {


    /**
     * result : 1
     * msg :
     * data : {"color_data":{"default_font_color":"#496383","Select_font_color":"#ffffff","menu_topColor":"#0a1f3a","menu_buttomColor":"#114282"},"list":[{"title":"全部分類","topColor":"#0a1f3a","buttomColor":"#114282","type":"6","info_data":"","store":"10"},{"title":"瀏覽平光鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"269","store":"9"},{"title":"瀏覽太陽鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"268","store":"8"},{"title":"折扣專區","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"270","store":"7"},{"title":"專題分享","topColor":"#0a1f3a","buttomColor":"#114282","type":"2","info_data":"","store":"6"},{"title":"我的購物車","topColor":"#0a1f3a","buttomColor":"#114282","type":"4","info_data":"","store":"5"}]}
     */

    private String result;
    private String msg;
    /**
     * color_data : {"default_font_color":"#496383","Select_font_color":"#ffffff","menu_topColor":"#0a1f3a","menu_buttomColor":"#114282"}
     * list : [{"title":"全部分類","topColor":"#0a1f3a","buttomColor":"#114282","type":"6","info_data":"","store":"10"},{"title":"瀏覽平光鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"269","store":"9"},{"title":"瀏覽太陽鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"268","store":"8"},{"title":"折扣專區","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"270","store":"7"},{"title":"專題分享","topColor":"#0a1f3a","buttomColor":"#114282","type":"2","info_data":"","store":"6"},{"title":"我的購物車","topColor":"#0a1f3a","buttomColor":"#114282","type":"4","info_data":"","store":"5"}]
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

    // 传递的是Object组成的List列表,所有
    public static class DataBean {
        /**
         * default_font_color : #496383
         * Select_font_color : #ffffff
         * menu_topColor : #0a1f3a
         * menu_buttomColor : #114282
         */

        private ColorDataBean color_data;
        /**
         * title : 全部分類
         * topColor : #0a1f3a
         * buttomColor : #114282
         * type : 6
         * info_data :
         * store : 10
         */

        private List<ListBean> list;

        public ColorDataBean getColor_data() {
            return color_data;
        }

        public void setColor_data(ColorDataBean color_data) {
            this.color_data = color_data;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ColorDataBean {
            private String default_font_color;
            private String Select_font_color;
            private String menu_topColor;
            private String menu_buttomColor;

            public String getDefault_font_color() {
                return default_font_color;
            }

            public void setDefault_font_color(String default_font_color) {
                this.default_font_color = default_font_color;
            }

            public String getSelect_font_color() {
                return Select_font_color;
            }

            public void setSelect_font_color(String Select_font_color) {
                this.Select_font_color = Select_font_color;
            }

            public String getMenu_topColor() {
                return menu_topColor;
            }

            public void setMenu_topColor(String menu_topColor) {
                this.menu_topColor = menu_topColor;
            }

            public String getMenu_buttomColor() {
                return menu_buttomColor;
            }

            public void setMenu_buttomColor(String menu_buttomColor) {
                this.menu_buttomColor = menu_buttomColor;
            }
        }

        public static class ListBean implements Parcelable{
            private String title;
            private String topColor;
            private String buttomColor;
            private String type;
            private String info_data;
            private String store;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopColor() {
                return topColor;
            }

            public void setTopColor(String topColor) {
                this.topColor = topColor;
            }

            public String getButtomColor() {
                return buttomColor;
            }

            public void setButtomColor(String buttomColor) {
                this.buttomColor = buttomColor;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getInfo_data() {
                return info_data;
            }

            public void setInfo_data(String info_data) {
                this.info_data = info_data;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            protected ListBean(Parcel in) {
                title = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(title);
            }
        }
    }
}