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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anushai on 12/20/17.
 */

public class OperatorLayout extends Fragment implements AsyncResponse{

    private ListView listView;
    private ListItemAdapter listItemAdapter;
    private List<ListItem> itemList;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AutoCompleteTextView autoCompleteTextView;
    Button button;
    View operatorView;
    String[] optAutoCompletelistName;
    ArrayList<String> autoCompleteArray = new ArrayList<>();
    AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        operatorView = inflater.inflate(R.layout.operator_layout,container,false);

        FloatingActionButton fab = (FloatingActionButton) operatorView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialContactPhone("+");
            }
        });

        if(!isNetworkAvailable()) {
            AlertBox();
        }

        WebService webService=new WebService(operatorView.getContext(),"get","Please wait");
        webService.asyncResponse=this;

        //webService.execute("http://104.196.101.2:3009/AllOperators/getOperatorName");
        webService.execute("http://35.227.78.254:3009/AllOperators/getOperatorName");

        MobileAds.initialize(operatorView.getContext(),
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = operatorView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return operatorView;
    }

    // make a call
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void processFinish(String output) {
        ArrayList<ListItem> arrayList=new ArrayList<>();
        button = operatorView.findViewById(R.id.button);

        try {

            jsonArray = new JSONArray(output);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                if(!jsonObject.getString("operatorName").equals("null")) {
                    arrayList.add(new ListItem(jsonObject.getString("operatorName"),
                            jsonObject.getString("mobileCode"), jsonObject.getInt("id")));


                    if ((autoCompleteArray.isEmpty()) || (!autoCompleteArray.contains(jsonObject.getString("operatorName")))) {
                        autoCompleteArray.add(jsonObject.getString("operatorName"));
                    }
                    autoCompleteArray.add(jsonObject.getString("mobileCode"));
                }
            }


            optAutoCompletelistName = autoCompleteArray.toArray(new String[autoCompleteArray.size()]);
            autoCompleteTextView = operatorView.findViewById(R.id.autoCompleteTextView);
            //Creating the instance of ArrayAdapter containing list of language names
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (operatorView.getContext(),android.R.layout.select_dialog_item,optAutoCompletelistName);
            autoCompleteTextView.setThreshold(1);//will start working from first character
            autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

            //button

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!autoCompleteTextView.getText().toString().equals("")
                           && (autoCompleteArray.contains(autoCompleteTextView.getText().toString()))) {

                        Bundle simple_bundle = new Bundle();
                        simple_bundle.putString("item1", String.valueOf(autoCompleteTextView.getText()));
                        simple_bundle.putString("item2", "operatorTab");

                        Intent intent = new Intent(operatorView.getContext(), SearchResult.class);
                        intent.putExtras(simple_bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(operatorView.getContext(), "Please enter valid name or code ", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            listView = (ListView) operatorView.findViewById(R.id.listViewOprt);

            listItemAdapter = new ListItemAdapter(operatorView.getContext(), arrayList);
            listView.setAdapter(listItemAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle simple_bundle = new Bundle();
                    simple_bundle.putString("item1", view.getTag().toString());
                    simple_bundle.putString("item2", "operatorTab");

                    Intent intent = new Intent(operatorView.getContext(), SearchResult.class);
                    intent.putExtras(simple_bundle);
                    startActivity(intent);

                }
            });

        } catch (JSONException e) {

            AlertError();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(operatorView.getContext(), "Error occurred. Please try again later", Toast.LENGTH_SHORT).show();


                }
            });
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) operatorView.getContext().getSystemService(operatorView.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //display alert when network is not available
    public void AlertBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(operatorView.getContext());
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

    public void AlertError(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(operatorView.getContext());
        alertDialogBuilder.setMessage("Error occurred. Please try again later");
        alertDialogBuilder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
