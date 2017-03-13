package mohammed.hemdan.weather.api.methods;

import mohammed.hemdan.weather.api.ApiKeys;
import mohammed.hemdan.weather.api.ApiUrls;
import mohammed.hemdan.weather.api.response.location.LocationCityResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mhemdan on 23/01/17.
 */

public interface LocationApi {
    @GET(ApiUrls.GET_LOCATION)
    Call<LocationCityResponse> execute(@Query(ApiKeys.LOCATION_KEY_ADDRESS) String address);
}
