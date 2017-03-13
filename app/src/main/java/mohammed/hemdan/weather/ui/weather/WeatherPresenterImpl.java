package mohammed.hemdan.weather.ui.weather;

import mohammed.hemdan.weather.api.ApiCallBack;
import mohammed.hemdan.weather.api.WeatherApiClient;

/**
 * Created by mhemdan on 24/01/17.
 */

public class WeatherPresenterImpl implements WeatherPresenter, ApiCallBack {
    private WeatherView view;
    private WeatherApiClient weatherApiClient;
    public WeatherPresenterImpl(WeatherView view){
        this.view = view;
        this.weatherApiClient = WeatherApiClient.getInstance();
    }
    @Override
    public void getWeatherForCity(String location) {
        this.weatherApiClient.getWeatherOfSpecificLocation(location,this);
    }

    @Override
    public void onSuccess(Object object) {
        this.view.insert(object);
    }

    @Override
    public void onFailure(Object object) {
        this.view.showError(object.toString());
    }
}
