package com.flowzinga.isthewifion;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button buttonCheck;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCheck = (Button) findViewById(R.id.button1);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
                textView4.setText("");
                textView5.setText("");
                checkWifi();
            }
        };

        buttonCheck.setOnClickListener(onClickListener1);

        checkWifi();
    }


    public void checkWifi(){

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // Check overall data connection
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            textView1.setText("You are not connected to the internet.");
            textView1.setBackgroundColor(getResources().getColor(R.color.orange));
            return;
        }
        else {
            String a = activeNetwork.getTypeName();
            textView1.setText("You are connected to the internet, and you are using " + a + " data.");
            textView1.setBackgroundColor(getResources().getColor(R.color.green));
        }

        // Check active data connection name
        String b = activeNetwork.getSubtypeName();
        String c = activeNetwork.toString();
//        textView2.setText("You are currently using " + a + " data.");

        // Wifi details
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            String d = activeNetwork.getExtraInfo();
            textView2.setText("You are connected to Wifi network: " + d);
        }
        else {
            textView2.setText("Wifi not connected");
        }


        // Mobile details
        NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            return ((netInfo != null) && netInfo.isConnected());

        String f = netInfo.getState().toString();
//        textView4.append("getState: " + f);

        if (netInfo.isAvailable()) {
            if (netInfo.isConnected()) {
                textView4.setText("Mobile Network is Connected.");
            } else {
                textView4.setText("Mobile Network is available but not Connected.");
            }
        }
        if (netInfo.isRoaming()) {
            textView4.append("Warning: You are on roaming.");
        } else {
            textView4.append("You are not on roaming.");
        }

        //to remove
        String e = netInfo.toString();
        textView5.setText(b + c + e);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
