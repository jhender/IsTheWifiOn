package com.flowzinga.isthewifion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button buttonCheck;
    Button buttonWifiOn;
    Button buttonWifiOff;
    Button buttonWifiSettings;
    Button buttonHotspotSettings;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
//    TextView textView5;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCheck = (Button) findViewById(R.id.button1);
        buttonWifiOn = (Button) findViewById(R.id.button_wifi_on);
        buttonWifiOff = (Button) findViewById(R.id.button_wifi_off);
        buttonWifiSettings = (Button) findViewById(R.id.button_wifi_settings);
        buttonHotspotSettings = (Button) findViewById(R.id.button_hotspot_settings);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
//        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
//        textView5 = (TextView) findViewById(R.id.textView5);

        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText("");
                textView2.setText("");
//                textView3.setText("");
                textView4.setText("");
//                textView5.setText("");
                checkWifi();
            }
        };

        View.OnClickListener onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnWifiOn();
            }
        };
        View.OnClickListener onClickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnWifiOff();
            }
        };
        View.OnClickListener onClickListener4 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));            }
        };
        View.OnClickListener onClickListener5 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.TetherSettings");
                startActivity(intent);
            }
        };


        buttonCheck.setOnClickListener(onClickListener1);
        buttonWifiOn.setOnClickListener(onClickListener2);
        buttonWifiOff.setOnClickListener(onClickListener3);
        buttonWifiSettings.setOnClickListener(onClickListener4);
        buttonHotspotSettings.setOnClickListener(onClickListener5);

        checkWifi();

        addShortcutIcon();
    }


    public void checkWifi(){

        // Rough network connectivity manager
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // Wifi Manager
//        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        // Mobile details
        NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            return ((netInfo != null) && netInfo.isConnected());

        // Check overall data connection
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        // NOT CONNECTED
        if (!isConnected) {
            textView1.setText("You are not connected to the internet.");
            textView1.setTextColor(getResources().getColor(R.color.orange));
//            return;

            // Is aeroplane mode on?
            if (isAirplaneModeOn()) {
                textView2.setText("Disconnected");
                textView2.setTextColor(getResources().getColor(R.color.orange));
                textView4.setText("Your phone is on Airplane mode. Please turn this off if you want to connect to the internet.");
                textView4.setTextColor(getResources().getColor(R.color.orange));

            } else {

                // Wifi details
                if (wifiManager.isWifiEnabled()) {
                    textView2.setText("Your wifi is Enabled but not Connected.");
                } else {
                    textView2.setText("Your wifi is disabled. Please enable to use Wifi.");
                }

            }




        }
        // CONNECTED
        else {
            String a = activeNetwork.getTypeName();
            textView1.setText("You are connected to the internet, and you are using " + a + " data.");
            textView1.setTextColor(getResources().getColor(R.color.green));

            // Wifi details
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                String d = activeNetwork.getExtraInfo();
                textView2.setText("You are connected to Wifi network: " + d);
                textView2.setTextColor(getResources().getColor(R.color.green));
            }
            else {
                textView2.setText("Wifi not connected");

                if (wifiManager.isWifiEnabled()) {
                    textView2.setText("Your Wifi is Enabled but not Connected.");
                } else {
                    textView2.setText("Your Wifi is Disabled.");
                    textView2.setTextColor(getResources().getColor(R.color.orange));
                }
            }

            String f = netInfo.getState().toString();
//        textView4.append("getState: " + f);

            if (netInfo.isAvailable()) {
                if (netInfo.isConnected()) {
                    textView4.setText("Mobile Network is Connected.");
                    textView4.setTextColor(getResources().getColor(R.color.green));

                } else {
                    textView4.setText("Mobile Network is available but not being used.");
                    textView4.setTextColor(getResources().getColor(R.color.green));

                }
            } else {
                textView4.setText("You are not connected to mobile network.");
                textView4.setTextColor(getResources().getColor(R.color.orange));
            }
            if (netInfo.isRoaming()) {
                textView4.append(" Warning: You are on roaming.");
                textView4.setTextColor(getResources().getColor(R.color.orange));

            } else {
//            textView4.append("You are not on roaming.");
            }

//            String e = netInfo.toString();

//            // Check active data connection name
//            String b = activeNetwork.getSubtypeName();
//            String c = activeNetwork.toString();
//            textView5.setText(b + c + e);
        }

    }

    private static boolean isAirplaneModeOn() {
        String a = Settings.Global.AIRPLANE_MODE_ON;
        return (a.equals("airplane_mode_on"));
    }

    private void turnWifiOn() {
        wifiManager.setWifiEnabled(true);
        checkWifi();
    }

    private void turnWifiOff() {
        wifiManager.setWifiEnabled(false);
        checkWifi();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    // Shortcut
    // onClick of addShortcutIcon
    private void addShortcutIcon() {
        //shorcutIntent object
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);
        //shortcutIntent is added with addIntent
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Is The Wifi On");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // finally broadcast the new Intent
        getApplicationContext().sendBroadcast(addIntent);
    }


}
