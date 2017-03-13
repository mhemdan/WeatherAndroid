package mohammed.hemdan.weather.api;


import android.util.Log;

import java.io.IOException;

import mohammed.hemdan.weather.api.methods.LocationApi;
import mohammed.hemdan.weather.api.response.location.LocationCityResponse;
import mohammed.hemdan.weather.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mhemdan on 23/01/17.
 */

public class LocationApiClient {
    private static LocationApiClient instance;
    private  Retrofit retrofit;
    public static LocationApiClient getInstance(){
        if(instance == null){
            instance = new LocationApiClient();
        }
        return instance;
    }
    private LocationApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrls.BASE_LOCATION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getCityAndLocationByWord(String cityName, final ApiCallBack apiCallBack) {
        LocationApi externalLogin = retrofit.create(LocationApi.class);
        Callback<LocationCityResponse> callback = new Callback<LocationCityResponse>() {
            @Override
            public void onResponse(Call<LocationCityResponse> call, Response<LocationCityResponse> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LocationCityResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    apiCallBack.onFailure(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE);
                }else{
                    apiCallBack.onFailure(Constants.RETROFIT_NOT_DEFINE_ERROR_MESSAGE);
                }
            }
        };

        Call<LocationCityResponse> call = externalLogin.execute(cityName);
        call.enqueue(callback);

    }
}
