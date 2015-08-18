package com.example.rohantiwari.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Device_Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device__menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device__menu, menu);
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
    public void onFindDeviceClick(View v)
    {
        if(v.getId() == R.id.find_device_meter)
        {
            Intent i = new Intent(Device_Menu.this,DeviceMeterActivity.class);
            startActivity(i);
        }
    }
    public void onProtectedModeClick(View v)
    {
        if(v.getId() == R.id.protected_mode_device)
        {
            Intent i = new Intent(Device_Menu.this,DeviceNotificationActivity.class);
            startActivity(i);
        }
    }
}
