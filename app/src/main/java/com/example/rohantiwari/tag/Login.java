package com.example.rohantiwari.tag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity {

    private EditText mEmailLogin;
    private EditText mPasswordLogin;
    private TextView _signupLink;
    private Button mLoginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailLogin = (EditText) findViewById(R.id.email_login);
        mPasswordLogin = (EditText) findViewById(R.id.password_login);
        mLoginbtn = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView)findViewById(R.id.link_login);

        mLoginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = mEmailLogin.getText().toString();
        String password = mPasswordLogin.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailLogin.getText().toString();
        String password = mPasswordLogin.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailLogin.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailLogin.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            mPasswordLogin.setError("greater than 4 alphanumeric characters");
            valid = false;
        } else {
            mPasswordLogin.setError(null);
        }

        return valid;
    }
}
