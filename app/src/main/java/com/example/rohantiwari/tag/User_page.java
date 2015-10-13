package com.example.rohantiwari.tag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class User_page extends ActionBarActivity {

    private LinearLayoutManager lLayout;
    public JSONArray json;
    private String success_login="false";
    private String result = "";
    private String username="";
    private String password="";

    private String rese="";
    public JSONArray jsonarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_act);
        setTitle(null);
        Bundle extras = getIntent().getExtras();
        username=extras.getString("username");
        password=extras.getString("password");

//        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(topToolBar);

        HttpAsyncTask httpt=new HttpAsyncTask();
        httpt.execute("http://128.199.83.48/devices/new.json");
        try {
            rese=httpt.get().toString();
            jsonarr= new JSONArray(rese);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //JSONArray arr=getJSONFromUrl();
        Log.d("final final", String.valueOf(jsonarr));
        List<ItemObject> rowListItem = null;
        try {
            rowListItem = getAllItemList();
        } catch (JSONException e) {
            Log.d("error json","what the fuck");
            e.printStackTrace();
        }

        lLayout = new LinearLayoutManager(User_page.this);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(User_page.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            Toast.makeText(User_page.this, "Refresh App", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_new){
            Intent i = new Intent(User_page.this,DeviceScanActivity.class);
            i.putExtra("username",username);
            i.putExtra("password", password);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private List<ItemObject> getAllItemList() throws JSONException {

        int num=2;
        List<ItemObject> allItems = new ArrayList<ItemObject>();
        try {
            Log.d("json length", String.valueOf(jsonarr.length()));
            num=jsonarr.length();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int i = 0; i < num; i++) {
            JSONObject obj= jsonarr.getJSONObject(i);
            Log.d("obje", String.valueOf(obj));
            allItems.add(new ItemObject(obj.get("device_mac").toString(),obj.get("device_name").toString()));
        }

        return allItems;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(User_page.this);
        @Override
        protected void onPreExecute() {
            // Show Progress dialog
            dialog.setMessage("Fetching..");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            try {
                json = new JSONArray(result);
                for(int i = 0; i < json.length(); i++){
                    JSONObject elem=json.getJSONObject(i);
                    Log.d("data rec", String.valueOf(elem));
          }
                Log.d("HTTP", "HTTP: OK");
                Log.d("Fetched data", String.valueOf(json));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        }
    public String POST(String url) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            if(is != null)
                result = convertInputStreamToString(is);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.e("HTTP", "Error in http connection " + e.toString());
        }
        return result;

    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
