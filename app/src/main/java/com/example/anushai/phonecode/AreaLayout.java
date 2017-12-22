package com.example.anushai.phonecode;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anushai on 12/20/17.
 */

public class AreaLayout extends Fragment implements AsyncResponse {

    private ListView listView;
    private ListItemAdapter listItemAdapter;
    private List<ListItem> itemList;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AutoCompleteTextView autoCompleteTextView;
    Button button;
    View areaView;

    String[] areaAutoCompletelistName;
    ArrayList<String> autoCompleteArray = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        areaView = inflater.inflate(R.layout.area_layout,container,false);

        // floating button
        FloatingActionButton fab = (FloatingActionButton) areaView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialContactPhone("+");
            }
        });



        if(!isNetworkAvailable()) {
            AlertBox();
        }

        WebService webService=new WebService(areaView.getContext(),"get","Please wait");
        webService.asyncResponse=this;

        webService.execute("http://192.168.8.102:3000/AllArea/getAreaName");


        return areaView;
    }

    // make a call
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void processFinish(String output) {
        ArrayList<ListItem> arrayList=new ArrayList<>();

        try {

            jsonArray = new JSONArray(output);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                arrayList.add(new ListItem(jsonObject.getString("areaName"),
                        jsonObject.getString("areaCode"), jsonObject.getInt("id")));
                autoCompleteArray.add(jsonObject.getString("areaName"));
                autoCompleteArray.add( jsonObject.getString("areaCode"));
            }

            areaAutoCompletelistName = autoCompleteArray.toArray(new String[autoCompleteArray.size()]);

            autoCompleteTextView = areaView.findViewById(R.id.autoCompleteTextViewArea);

            //Creating the instance of ArrayAdapter containing list of language names
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (areaView.getContext(),android.R.layout.select_dialog_item,areaAutoCompletelistName);
            autoCompleteTextView.setThreshold(1);//will start working from first character
            autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

            //button
            button = areaView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!autoCompleteTextView.getText().toString().equals("")
                            && (autoCompleteArray.contains(autoCompleteTextView.getText().toString()))) {
                        Toast.makeText(areaView.getContext(), "clicked on " + autoCompleteTextView.getText(), Toast.LENGTH_SHORT).show();
                        Bundle simple_bundle = new Bundle();
                        simple_bundle.putString("item1", String.valueOf(autoCompleteTextView.getText()));
                        simple_bundle.putString("item2", "areaTab");

                        Intent intent = new Intent(areaView.getContext(), SearchResult.class);
                        intent.putExtras(simple_bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(areaView.getContext(), "Please enter valid name or code ", Toast.LENGTH_SHORT).show();

                    }
                }
            });


            listView = (ListView) areaView.findViewById(R.id.listViewArea);

            listItemAdapter = new ListItemAdapter(areaView.getContext(), arrayList);
            listView.setAdapter(listItemAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle simple_bundle = new Bundle();
                    simple_bundle.putString("item1", view.getTag().toString());
                    simple_bundle.putString("item2", "areaTab");

                    Intent intent = new Intent(areaView.getContext(), SearchResult.class);
                    intent.putExtras(simple_bundle);
                    startActivity(intent);

                    Toast.makeText(areaView.getContext(),"clicked on "+ view.getTag(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) areaView.getContext().getSystemService(areaView.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //display alert when network is not available
    public void AlertBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(areaView.getContext());
        alertDialogBuilder.setMessage("Internet not available, Please check your internet connectivity and try again");
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertDialogBuilder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent=new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
