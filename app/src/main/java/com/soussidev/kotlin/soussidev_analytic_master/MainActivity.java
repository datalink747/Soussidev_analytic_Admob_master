package com.soussidev.kotlin.soussidev_analytic_master;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.soussidev.kotlin.soussidev_analytic_master.analytic.AnalyticsApplication;
import com.soussidev.kotlin.soussidev_analytic_master.analytic.Config;

public class MainActivity extends AppCompatActivity {
    private static String TAG1 = MainActivity.class.getSimpleName();
    /**
     * The {@link Tracker} used to record screen views.
     */
    private Tracker mTracker;
    private final Config config =new Config();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make sure global_tracker.xml is configured
       /* if (!checkConfiguration()) {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, R.string.bad_config, Snackbar.LENGTH_INDEFINITE).show();
        }*/


        if(!config.checkConfiguration(this,TAG1))
        {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, R.string.bad_config, Snackbar.LENGTH_INDEFINITE).show();
        }

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
       /* AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();*/
        config.StartTraker(MainActivity.this);
        // [END shared_tracker]

        // Send Screen Name:
        /*mTracker.setScreenName("Curent Activity" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
        config.getCurrentScreen(TAG1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // [START custom_event]
               /* mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Fab Button")
                        .setAction("Go to Admob Activity")
                        .build());*/
               config.getEventName("Fab Button","Go to Admob Activity",null);
                // [END custom_event]

                Intent sendIntent = new Intent(MainActivity.this,Admob.class);
               // MainActivity.this.finish();
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        config.getCurrentScreen(MainActivity.class.getSimpleName().toUpperCase());
    }

    /**
     * Check to make sure global_tracker.xml was configured correctly (this function only needed
     * for sample apps).
     */
    /*private boolean checkConfiguration() {
        XmlResourceParser parser = getResources().getXml(R.xml.global_tracker);

        boolean foundTag = false;
        try {
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = parser.getName();
                    String nameAttr = parser.getAttributeValue(null, "name");

                    foundTag = "string".equals(tagName) && "ga_trackingId".equals(nameAttr);
                }

                if (parser.getEventType() == XmlResourceParser.TEXT) {
                    if (foundTag && parser.getText().contains("REPLACE_ME")) {
                        return false;
                    }
                }

                parser.next();
            }
        } catch (Exception e) {
            Log.w(TAG, "checkConfiguration", e);
            return false;
        }

        return true;
    }*/

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
            config.getEventName("Menu","Action setting",TAG1);
            return true;
        }
        if (id == R.id.action_shutdown) {
            //Get Event Shutdown
            config.getEventName("Menu","Action shutdown",TAG1);

            //Start Shutdown
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int answer = 12 / 0;
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 1500);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
