package com.example.dllo.mirror.fragmentworks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.MainActivity;
import com.example.dllo.mirror.adapterworks.MenuFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;


/**
 * Created by dllo on 16/3/29.
 */
public class MenuFragment extends BaseFragment implements StaticEntityInterface {

    private ListView listView;
    private MenuFragmentAdapter adapter;
    MenuFragmentBean bean;

    //    LinearLayout layout;
    LinearLayout fragment_menu_line;

    @Override
    protected int initLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView() {
        fragment_menu_line = bindView(R.id.fragment_menu_line);
        listView = bindView(R.id.fragment_menu_listview);
//        layout = bindView(R.id.fragment_menu_all_line);

    }

    @Override
    protected void initData() {
//        layout.setOnClickListener(this);

        // ******* 请求网络数据并解析 ******* //
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(TOKEN, "");
//        builder.add(UID,"");
        builder.add(DEVICE_TYPE, "2");
//        builder.add(PAGE,"");
//        builder.add(LAST_TIME,"");
//        builder.add(GOODS_LIST_CATEGORY_ID,"");
//        builder.add(GOODS_LIST_VERSION,"");

        httpNetHelper.getPostDataFromNet(builder, MENU_LIST, new NetListener() {
            @Override
            public void getSuccess(final String s) {
                Log.d("数据", s);

                // 指出Fragment 的寄主activity, 更新UI需要主线程来更新,所以复写Activity 的这个方法更新,
                // 另一个方法是用Handler的对象,用handleMessage回调函数调用更新界面显示的函数
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bean = new Gson().fromJson(s.toString(), MenuFragmentBean.class);
                        adapter = new MenuFragmentAdapter(bean);
                        listView.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void getFail(String s) {

            }
        });
    }
    // ********************************//

//    @Override
//    public void onResume() {
//       setAnimation(fragment_menu_line);
//        super.onResume();
//    }


    //    设置动画
    private void setAnimation(View tv) {
        ObjectAnimator.ofFloat(tv, "translationX", 0F, 60F).setDuration(400).start();
        ObjectAnimator.ofFloat(tv, "translationY", 0F, 40F).setDuration(400).start();
    }

    //    设置广播
    private void setBroadRecive() {
        Intent intent = new Intent();
        intent.setAction("activityworks");
        getActivity().sendBroadcast(intent);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.fragment_menu_all_line:
//                setBroadRecive();
//                break;
//        }
//    }
}
