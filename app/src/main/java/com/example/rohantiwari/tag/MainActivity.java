package com.example.rohantiwari.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onSignUpClick(View v) {
        if (v.getId() == R.id.sign_up) {
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
            finish();
        }
        else if(v.getId()==R.id.login_btn)
        {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
    }

}
