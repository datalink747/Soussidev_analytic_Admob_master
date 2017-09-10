package com.soussidev.kotlin.soussidev_analytic_master.analytic;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.soussidev.kotlin.soussidev_analytic_master.R;

/**
 * Created by Soussi on 10/09/2017.
 */

public class Config {

    private Tracker mTracker;
    private static Config mInstance;

    public Config() {

    }
    /**
     * @auteur Soussi Mohamed .
     * @param ct
     * @param TAG Get Name Activity
     */
    public boolean checkConfiguration(Context ct,String TAG) {
        XmlResourceParser parser = ct.getResources().getXml(R.xml.global_tracker);

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
    }
    /**
     * @auteur Soussi Mohamed .
     * @param ct Get Context
     *
     */
    public void StartTraker(Activity ct)
    {

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) ct.getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]
        mTracker.enableAutoActivityTracking(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableExceptionReporting(true);

    }


    /**
     * @auteur Soussi Mohamed .
     *
     * @param tag Get Name of Screen
     */
    public void getCurrentScreen(String tag)
    {
// Send Screen Name:
        mTracker.setScreenName("Curent Activity :" + tag);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    /**
     * @auteur Soussi Mohamed .
     * @param category Get category
     * @param action Get Action
     * @param labl lable
     */
    public void getEventName(String category,String action,String labl)
    {
        // [START custom_event]
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(labl)
                .build());
        // [END custom_event]
    }
    /**
     * @auteur Soussi Mohamed .
     * @param ct Context
     * @param e Get Exception
     * @param fatal Get Priority (true or false)
     *
     */

    public void getCracheActivity(Activity ct, Exception e,Boolean fatal)
    {
        mTracker.send(new HitBuilders.ExceptionBuilder()
                .setDescription(
                        new StandardExceptionParser(ct, null)
                                .getDescription(Thread.currentThread().getName(), e))
                                .setFatal(fatal)
                                .build());
    }


}
