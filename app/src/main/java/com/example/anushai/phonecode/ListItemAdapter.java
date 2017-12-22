package com.example.anushai.phonecode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anushai on 12/21/17.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context context;
    private List<ListItem> listItems;

    public ListItemAdapter(Context context, List<ListItem> listItems){
        this.context=context;
        this.listItems=listItems;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_list_view_item, null);
        TextView nameTextView = (TextView)v.findViewById(R.id.name);
        TextView codeTextView = (TextView)v.findViewById(R.id.code);

        nameTextView.setText(listItems.get(i).getName());
        codeTextView.setText(listItems.get(i).getCode());

        v.setTag(listItems.get(i).getName());

        return v;
    }
}
