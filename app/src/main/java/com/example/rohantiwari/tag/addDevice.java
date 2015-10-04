package com.example.rohantiwari.tag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by siddharthsingh on 20/08/15.
 */
public class addDevice extends Activity implements View.OnClickListener {
    public String deviceMac="";
    public String deviceName="";
    public TextView mac;
    public String username;
    public String password;
    public EditText name;
    public Button addbt;
    public String result="";
    public JSONObject json;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddevice_act);
        Bundle extras = getIntent().getExtras();
        deviceMac=extras.getString("device_mac");
        Log.d("device_mac", deviceMac);
        mac=(TextView)findViewById(R.id.textView2);
        mac.setText(deviceMac);
        addbt=(Button)findViewById(R.id.bt_add);
        name=(EditText)findViewById(R.id.editText);
        username=extras.getString("username");
        password=extras.getString("password");
        addbt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_add:
                Log.d("got it","yo");
                dispatchTakePictureIntent();
                break;

        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(addDevice.this);

        protected void onPreExecute() {
            // Show Progress dialog
            dialog.setMessage("Adding Device..");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... urls) {

            deviceName=name.getText().toString();

            return POST(urls[0],deviceName,deviceMac);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toast.makeText(getBaseContext(), "Check Email", Toast.LENGTH_LONG).show();
            try {
                json = new JSONObject(result);
                Log.d("Data recieved from server", String.valueOf(json));

                Intent i = new Intent(getApplicationContext(), User_page.class);
                startActivity(i);
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
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
    public String POST(String url,String name, String devicem ){
        InputStream inputStream = null;
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("device_name",name);
            jsonObject.put("device_mac",devicem );
            jsonObject.put("device_latitude","12.000");
            jsonObject.put("device_longitude","11.000");
            Log.d("json obj", String.valueOf(jsonObject));
            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        Log.d("final result", result);
        return result;
    }

}
