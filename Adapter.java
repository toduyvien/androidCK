package com.example.bai1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<User> list;

    public Adapter(Context context,int layout, ArrayList<User> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public User getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
class Holder{

        TextView et_ten,et_sdt,et_matkhau;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder ;
        holder=new Holder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


            convertView = inflater.inflate(layout,null);
            holder.et_ten=convertView.findViewById(R.id.et_tenadapter);
            holder.et_sdt=convertView.findViewById(R.id.et_sdtadapter);
            holder.et_matkhau=convertView.findViewById(R.id.et_matkhauadapter);

            convertView.setTag(holder);
        }
        else {
            holder= (Holder) convertView.getTag();
        }
        User user=list.get(position);

        holder.et_ten.setText(user.getTen());
        holder.et_sdt.setText(user.getSdt());
        holder.et_matkhau.setText(user.getMatkhau());
        return convertView;
    }
}
