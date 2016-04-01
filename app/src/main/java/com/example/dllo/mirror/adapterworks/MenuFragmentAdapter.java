package com.example.dllo.mirror.adapterworks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.bean.MenuFragmentBean;

/**
 * Created by dllo on 16/3/31.
 */
public class MenuFragmentAdapter extends BaseAdapter {

    MenuFragmentBean bean;

    public MenuFragmentAdapter(MenuFragmentBean bean){
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_menu_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_menu = (TextView) convertView.findViewById(R.id.fragment_menu_item_tv_tab);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_menu.setText(bean.getData().getList().get(position).getTitle());
        return convertView;
    }


    class ViewHolder{
        TextView tv_menu;
    }
}
