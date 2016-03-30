package com.example.dllo.mirror.fragmentworks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseFragment;



/**
 * Created by dllo on 16/3/29.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {
    LinearLayout layout;
    LinearLayout fragment_menu_line;

    @Override
    protected int initLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView() {
        fragment_menu_line = bindView(R.id.fragment_menu_line);
        layout = bindView(R.id.fragment_menu_all_line);
    }

    @Override
    protected void initData() {
        layout.setOnClickListener(this);
    }


    @Override
    public void onResume() {
       setAnimation(fragment_menu_line);
        super.onResume();
    }




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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_menu_all_line:
                setBroadRecive();
                break;
        }
    }
}
