package mohammed.hemdan.weather.api.methods;

import mohammed.hemdan.weather.api.ApiKeys;
import mohammed.hemdan.weather.api.ApiUrls;
import mohammed.hemdan.weather.api.response.location.LocationCityResponse;
import mohammed.hemdan.weather.api.response.weather.WeatherCityResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mhemdan on 23/01/17.
 */

public interface WeatherApi {
    @GET(ApiUrls.GET_CITY_WEATHER)
    Call<WeatherCityResponse> execute(@Path("LOCATION") String location);
}
