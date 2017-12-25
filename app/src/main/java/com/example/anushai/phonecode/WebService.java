package com.example.anushai.phonecode;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anushai on 12/22/17.
 */

public class WebService extends AsyncTask<String, Void, String> {

    String data = "";
    public AsyncResponse asyncResponse;
    private String request_type;
    private Context context;
    private ProgressDialog progressDialog;
    private String message;


    public WebService(){

    }

    public WebService(String type,String m){
        message=m;
        request_type=type;
        progressDialog =new ProgressDialog(context);
    }

    public WebService(Context ctx,String type,String m)
    {
        request_type = type;
        context = ctx;
        progressDialog =new ProgressDialog(context);
        message = m;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage(message);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        String urlName = params[0].toString();
        try {
            URL url = new URL(urlName);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line=bufferedReader.readLine()) != null){
                System.out.println(line);
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        asyncResponse.processFinish(this.data);

        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
