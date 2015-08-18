package com.example.rohantiwari.tag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Login extends Activity implements View.OnClickListener {

    private EditText mEmailLogin;
    private EditText mPasswordLogin;
    private TextView _signupLink;
    private Button mLoginbtn;
    private String success_login="false";
    private String result = "";

    public JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailLogin = (EditText) findViewById(R.id.email_login);
        mPasswordLogin = (EditText) findViewById(R.id.password_login);
        mLoginbtn = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_login);
        mLoginbtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // call AsynTask to perform network operation on separate thread
//                ProgressDialog dialog = new ProgressDialog(Login.this);
//                dialog.setMessage("Authenticating..");
//                dialog.show();
                new HttpAsyncTask().execute("http://192.168.56.74:8001/token/new.json");

//                Log.d("final result of login", success_login);
//                if(success_login=="true") {
//                    Intent i = new Intent(Login.this, User_page.class);
//                    startActivity(i);
//                    finish();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_SHORT).show();
//                }
//
//                break;

        }
    }
    public String POST(String url) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", mEmailLogin.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", mPasswordLogin.getText().toString()));
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
            //json = new JSONObject(result);
            //JSONObject json_LL = json.getJSONObject("LL");
            //success_login=json.getString("success");
            //Log.d("success value",success_login);
            //Log.d("HTTP", "HTTP: OK");
            //Log.d("token received",result);
        } catch (Exception e) {
            Log.e("HTTP", "Error in http connection " + e.toString());
        }
        return result;

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(Login.this);
        @Override
        protected void onPreExecute() {
            // Show Progress dialog
            dialog.setMessage("Authenticating..");
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
                json = new JSONObject(result);
//                JSONObject json_LL = json.getJSONObject("LL");
                success_login=json.getString("success");
                Log.d("success value",success_login);
                Log.d("HTTP", "HTTP: OK");
                Log.d("token received",result);
                if(success_login=="true") {
                    Intent i = new Intent(getApplicationContext(), User_page.class);
                    i.putExtra("username", mEmailLogin.getText().toString());
                    i.putExtra("password", mPasswordLogin.getText().toString());
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_SHORT).show();

                }

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
}
