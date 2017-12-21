package com.example.anushai.phonecode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anushai on 12/20/17.
 */

public class countryLayout extends Fragment {

    private ListView listView;
    private ListItemAdapter listItemAdapter;
    private List<ListItem> itemList;
    View countryView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        countryView = inflater.inflate(R.layout.country_layout,container,false);

        listView = (ListView) countryView.findViewById(R.id.listView);


        itemList = new ArrayList<>();
        itemList.add(new ListItem("SL", "094",1));
        itemList.add(new ListItem("USA", "01",2));
        itemList.add(new ListItem("Canada", "04",3));
        itemList.add(new ListItem("India", "456",4));
        itemList.add(new ListItem("Korea", "85",5));
        itemList.add(new ListItem("Japan", "54",6));


        listItemAdapter = new ListItemAdapter(countryView.getContext(), itemList);
        listView.setAdapter(listItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(countryView.getContext(),"clicked on "+ view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        return countryView;
    }

}
