package mohammed.hemdan.weather.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import mohammed.hemdan.weather.ui.HomeActivity;
import mohammed.hemdan.weather.ui.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}