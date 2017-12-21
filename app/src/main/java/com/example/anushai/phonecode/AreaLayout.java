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

public class AreaLayout extends Fragment {

    private ListView listView;
    private ListItemAdapter listItemAdapter;
    private List<ListItem> itemList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View areaView = inflater.inflate(R.layout.area_layout,container,false);

        listView = (ListView) areaView.findViewById(R.id.listViewArea);


        itemList = new ArrayList<>();
        itemList.add(new ListItem("Galle", "091",1));
        itemList.add(new ListItem("Colombo", "011",2));
        itemList.add(new ListItem("kandy", "044",3));
        itemList.add(new ListItem("matara", "026",4));
        itemList.add(new ListItem("anuradapura", "085",5));
        itemList.add(new ListItem("polonnaruwa", "04",6));
        itemList.add(new ListItem("hambantota", "024",7));
        itemList.add(new ListItem("nuwara eliya", "054",8));


        listItemAdapter = new ListItemAdapter(areaView.getContext(), itemList);
        listView.setAdapter(listItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(areaView.getContext(),"clicked on "+ view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return areaView;
    }
}
