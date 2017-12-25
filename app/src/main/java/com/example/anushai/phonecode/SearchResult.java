package com.example.anushai.phonecode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity implements AsyncResponse {

    String name;
    int phoneCode;
    TextView titleText;
    TextView textName;
    TextView textCode;
    TextView textIdd;
    TextView text9;

    TextView titletextName;
    TextView titletextCode;
    TextView titletextIdd;
    TextView titletxtView8;

    String tabtype;
    JSONArray jsonArray;
    JSONObject jsonObject;
    AdView mAdView;

    //String URL_CONSTANT = "http://104.196.101.2:3009";
    String URL_CONSTANT = "http://35.227.78.254:3009";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleText = findViewById(R.id.textView);


        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        name=my_bundle_received.get("item1").toString();
        tabtype = my_bundle_received.get("item2").toString();
        titleText.setText(name);

        try {
            phoneCode = Integer.parseInt(name);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialContactPhone(name);
                }
            });

            if(tabtype.equals("countryTab")){
                WebService webService=new WebService(this,"get","Please wait");
                webService.asyncResponse=this;

                webService.execute(URL_CONSTANT+"/"+String.valueOf(phoneCode));
            }if(tabtype.equals("areaTab")){

                WebService webService=new WebService(this,"get","Please wait");
                webService.asyncResponse=this;

                webService.execute(URL_CONSTANT+"/AllArea/getAreaName/"+name);
            }if(tabtype.equals("operatorTab")){
                WebService webService=new WebService(this,"get","Please wait");
                webService.asyncResponse=this;

                webService.execute(URL_CONSTANT+"/AllOperators/getOperatorName/"+name);
            }

        }catch (Exception e){

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialContactPhone("+");
                }
            });

            String replacedName = name.replace(" ","%20");
            if(tabtype.equals("countryTab")) {
                WebService webService = new WebService(this, "get", "Please wait");
                webService.asyncResponse = this;

                webService.execute(URL_CONSTANT+"/getCountry/" +replacedName);
            }if(tabtype.equals("areaTab")){
                WebService webService=new WebService(this,"get","Please wait");
                webService.asyncResponse=this;

                webService.execute(URL_CONSTANT+"/AllArea/getAreaCode/"+name);
            }if(tabtype.equals("operatorTab")){
                WebService webService=new WebService(this,"get","Please wait");
                webService.asyncResponse=this;

                webService.execute(URL_CONSTANT+"/AllOperators/getOperatorCode/"+name);
            }
        }

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    // make a call
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void processFinish(String output) {


        ArrayList<ListItem> arrayList=new ArrayList<>();
        textName = findViewById(R.id.textView5);
        textCode = findViewById(R.id.textView6);
        textIdd = findViewById(R.id.textView7);
        titletextName = findViewById(R.id.textView2);
        titletextCode = findViewById(R.id.textView3);
        titletextIdd = findViewById(R.id.textView4);
        titletxtView8=findViewById(R.id.textView8);
        text9 = findViewById(R.id.textView9);

        String multiName = "";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        try {
            jsonArray = new JSONArray(output);

            if(tabtype.equals("countryTab")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    multiName += jsonObject.getString("countryName") + "\n";
                }
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            dialContactPhone(jsonObject.getString("countryCode"));
                        } catch (JSONException e) {
                            dialContactPhone("+");
                        }
                    }
                });
                if (jsonArray.length() == 1) {
                    textName.setText(jsonObject.getString("countryName"));
                    textCode.setText(jsonObject.getString("countryCode"));
                    textIdd.setText(jsonObject.getString("countryIdd"));
                    titletextName.setText("Country Name - ");
                    titletextCode.setText("Country Code  - ");
                    titletextIdd.setText("Country IDD     - ");
                } else {

                    textName.setText(multiName);
                    textCode.setText(jsonObject.getString("countryCode"));
                    textIdd.setText(jsonObject.getString("countryIdd"));
                    titletextName.setText("Country Name - ");
                    titletextCode.setText("Country Code  - ");
                    titletextIdd.setText("Country IDD     - ");

                    Toast.makeText(this, "Multiple results ", Toast.LENGTH_SHORT).show();

                }

            }if(tabtype.equals("areaTab")){

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);

                }
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            dialContactPhone(jsonObject.getString("areaCode"));
                        } catch (JSONException e) {
                            dialContactPhone("+");
                        }
                    }
                });
                if (jsonArray.length() == 1) {
                    textName.setText(jsonObject.getString("province"));
                    textCode.setText(jsonObject.getString("district"));
                    textIdd.setText(jsonObject.getString("areaName"));
                    text9.setText(jsonObject.getString("areaCode"));
                    titletextName.setText("Province       - ");
                    titletextCode.setText("District         - ");
                    titletextIdd.setText("Area Name  - ");
                    titletxtView8.setText("Area Code   - ");
                } else {
                    Toast.makeText(this, "Multiple results ", Toast.LENGTH_SHORT).show();

                }
            }if(tabtype.equals("operatorTab")){
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    multiName += jsonObject.getString("mobileCode") + "\n";
                }
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            dialContactPhone(jsonObject.getString("mobileCode"));
                        } catch (JSONException e) {
                            dialContactPhone("+");
                        }
                    }
                });

                if (jsonArray.length() == 1) {
                    textName.setText(jsonObject.getString("operatorName"));
                    textCode.setText(jsonObject.getString("mobileCode"));
                    titletextName.setText("Operator Name - ");
                    titletextCode.setText("Operator Code  - ");
                } else {
                    textName.setText(jsonObject.getString("operatorName"));
                    textCode.setText(multiName);
                    titletextName.setText("Operator Name - ");
                    titletextCode.setText("Operator Code  - ");

                    Toast.makeText(this, "Multiple results ", Toast.LENGTH_SHORT).show();

                }

            }
        }catch (Exception e){
            Toast.makeText(this,"Something went wrong ", Toast.LENGTH_SHORT).show();

        }
    }
}
