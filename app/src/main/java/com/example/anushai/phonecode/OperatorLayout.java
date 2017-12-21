package com.example.anushai.phonecode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anushai on 12/20/17.
 */

public class OperatorLayout extends Fragment {

    private ListView listView;
    private ListItemAdapter listItemAdapter;
    private List<ListItem> itemList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View operatorView = inflater.inflate(R.layout.operator_layout,container,false);


        listView = (ListView) operatorView.findViewById(R.id.listViewOprt);


        itemList = new ArrayList<>();
        itemList.add(new ListItem("Mobitel", "071/070",1));
        itemList.add(new ListItem("Dialog", "077/076",2));
        itemList.add(new ListItem("Etisalat", "072",3));
        itemList.add(new ListItem("Airtel", "074",4));
        itemList.add(new ListItem("Hutch", "073",5));


        listItemAdapter = new ListItemAdapter(operatorView.getContext(), itemList);
        listView.setAdapter(listItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(operatorView.getContext(),"clicked on "+ view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return operatorView;
    }
}
