package com.mpz.wifitimezone.app;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.util.Log;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

public class MainActivity extends Activity implements OnRefreshListener {
    private ProgressBar mProgress;
    private PullToRefreshLayout mPullToRefreshLayout;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

        ActionBarPullToRefresh.from(this)
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set the OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

        final Thread timerThread = new Thread() {
            @Override
            public void run() {
                mProgress.setVisibility(View.INVISIBLE);
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                String ipString =  Formatter.formatIpAddress(ip);
                Log.i("onCreate", "Thread " + ipString );
            }
        };
        timerThread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onRefreshStarted(View view) {
        mProgress.setVisibility(View.VISIBLE);
        mPullToRefreshLayout.setRefreshComplete();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

}
