package mohammed.hemdan.weather.util;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import m.hemdan.stepocrash.StepoCrash;

/**
 * Created by mhemdan on 23/01/17.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        StepoCrash.init(this);
        init();
    }
    private void init(){
        initNoSqlDB();
        initNetworkListener();
        initFont();

    }
    private void initNoSqlDB(){
        Paper.init(this);
    }
    private void initNetworkListener(){
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
    private void initFont(){
        FontsOverride.setDefaultFont(this, "DEFAULT", "ABOVE  - PERSONAL USE ONLY.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "ABOVE  - PERSONAL USE ONLY.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "ABOVE  - PERSONAL USE ONLY.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "ABOVE  - PERSONAL USE ONLY.ttf");
    }
}
