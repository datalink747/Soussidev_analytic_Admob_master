# Soussidev_analytic_Admob_master
Simple project include Google Analytic and Google Admob
<hr>
<a href='https://ko-fi.com/A243447K' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
<br>

# Code
* Config.class :
```java
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
```
* AnalyticsApplication.class :

```java
public class AnalyticsApplication extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}

```
* Mainactivity.class :

```java
// Make sure global_tracker.xml is configured
if(!config.checkConfiguration(this,TAG1))
        {
            View contentView = findViewById(android.R.id.content);
            Snackbar.make(contentView, R.string.bad_config, Snackbar.LENGTH_INDEFINITE).show();
        }
        
      //  Obtain the shared Tracker instance
        config.StartTraker(MainActivity.this);
        
     // Get Screen Name   
        config.getCurrentScreen(MainActivity.class.getSimpleName());
        
     // Get Event    
         if (id == R.id.action_settings) {
            config.getEventName("Menu","Action setting",TAG1);
            return true;
        }
```

# Linkedin
<a href="https://www.linkedin.com/in/soussimohamed/">
<img src="picture/linkedin.png" height="100" width="100" alt="Soussi Mohamed">
</a>

# Licence

```
Copyright 2017 Soussidev, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
