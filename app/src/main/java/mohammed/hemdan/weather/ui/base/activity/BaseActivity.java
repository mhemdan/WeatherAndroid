package mohammed.hemdan.weather.ui.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.List;

import mohammed.hemdan.weather.ui.base.BaseView;
import mohammed.hemdan.weather.util.Constants;

/**
 * Created by mhemdan on 24/01/17.
 */

public class BaseActivity extends AppCompatActivity implements BaseView, ConnectivityChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void showError(String message) {

    }

    @Override
    public void refresh() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if(event.getState().getValue() == ConnectivityState.CONNECTED){
            refresh();
        }else if(event.getState().getValue() == ConnectivityState.DISCONNECTED){
            showError(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE);
        }
        preformActionToAttachedFragments(event.getState());
    }

    public void preformActionToAttachedFragments(ConnectivityState action) {
        List<Fragment> allFragments = getSupportFragmentManager().getFragments();
        if (allFragments == null || allFragments.isEmpty()) {
            return;
        }
        for (Fragment fragment : allFragments) {
            if(fragment!=null)
                if (fragment.isVisible() && fragment instanceof BaseView) {
                    if (action.getValue() == ConnectivityState.DISCONNECTED) {
                        ((BaseView) fragment).showError(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE);
                    } else if (action.getValue() == ConnectivityState.CONNECTED) {
                        ((BaseView) fragment).refresh();
                    }
                }
        }
    }
}
