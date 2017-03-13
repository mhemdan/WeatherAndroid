package mohammed.hemdan.weather.api;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import m.hemdan.stepocrash.StepoCrash;
import mohammed.hemdan.weather.api.methods.LocationApi;
import mohammed.hemdan.weather.api.methods.WeatherApi;
import mohammed.hemdan.weather.api.response.location.LocationCityResponse;
import mohammed.hemdan.weather.api.response.weather.WeatherCityResponse;
import mohammed.hemdan.weather.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mhemdan on 23/01/17.
 */

public class WeatherApiClient {

    private static WeatherApiClient instance;
    private Retrofit retrofit;
    public static WeatherApiClient getInstance(){
        if(instance == null){
            instance = new WeatherApiClient();
        }
        return instance;
    }
    private WeatherApiClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrls.BASE_WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
    public void getWeatherOfSpecificLocation(String location , final ApiCallBack apiCallBack){
        WeatherApi externalLogin = retrofit.create(WeatherApi.class);
        StepoCrash.addStep(location);
        Callback<WeatherCityResponse> callback = new Callback<WeatherCityResponse>() {
            @Override
            public void onResponse(Call<WeatherCityResponse> call, Response<WeatherCityResponse> response) {
                apiCallBack.onSuccess(response.body());
                StepoCrash.addStep(response.body());
                Crashlytics.getInstance().crash();
            }

            @Override
            public void onFailure(Call<WeatherCityResponse> call, Throwable t) {
                StepoCrash.addStep(t.getLocalizedMessage());
                if (t instanceof IOException) {
                    apiCallBack.onFailure(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE);
                }else{
                    apiCallBack.onFailure(Constants.RETROFIT_NOT_DEFINE_ERROR_MESSAGE);
                }
            }
        };

        Call<WeatherCityResponse> call = externalLogin.execute(location);
        call.enqueue(callback);

    }
}
