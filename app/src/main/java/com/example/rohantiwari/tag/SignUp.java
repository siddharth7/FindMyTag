package com.example.rohantiwari.tag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUp extends Activity {
    private EditText mUserNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mCreateAccountButton;

    private String mEmail;
    private String mUsername;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameEditText = (EditText) findViewById(R.id.name);
        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.password_confirm);

        mCreateAccountButton = (Button) findViewById(R.id.btnCreate_acc);


        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mCreateAccountButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        mUsername = mUserNameEditText.getText().toString();
        mEmail = mEmailEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();


        //Add the login to server logic here Sid

        progressDialog.dismiss();
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mCreateAccountButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mUserNameEditText.getText().toString();
        String email =  mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String confirmpassword = mConfirmPasswordEditText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mUserNameEditText.setError("at least 3 characters");
            valid = false;
        } else {
            mUserNameEditText.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            mPasswordEditText.setError("greater than 4 alphanumeric characters");
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }
        if(!password.equals(confirmpassword)){
            mPasswordEditText.setError("Password Donot Match");
            valid  = false;
        }else {
            mPasswordEditText.setError(null);
        }
        return valid;
    }
}
