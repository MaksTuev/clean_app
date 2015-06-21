package com.two_man.setmaster;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    AudioManager am;
    Intent intent  = new Intent("android.service.notification.NotificationListenerService");
    ServiceConnection sConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //Log.d(LOG_TAG, "MainActivity onServiceConnected");
            //bound = true;
        }

    public void onServiceDisconnected(ComponentName name) {
        //Log.d(LOG_TAG, "MainActivity onServiceDisconnected");

        //bound = false;
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bindService(intent, sConn, BIND_AUTO_CREATE);
        ///testStartService();
        am = (AudioManager)getSystemService(AUDIO_SERVICE);

        Button silence = (Button)findViewById(R.id.silence);
        silence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        });

        Button vibrate = (Button)findViewById(R.id.vibrate);
        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                setNet(false, MainActivity.this);
            }
        });

        Button normal = (Button)findViewById(R.id.normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                setNet(true, MainActivity.this);

            }
        });

        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

    }


    private void setNet(boolean enable, Context context){
        try
        {
           /* if (Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1)
            {
                Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, defaultDisplay.getRotation());
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
            }
            else
            {
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
            }*/

            Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enable ? 1 : 0);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void testStartService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
    }



}
