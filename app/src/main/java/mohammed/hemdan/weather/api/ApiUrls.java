package mohammed.hemdan.weather.api;

/**
 * Created by mhemdan on 23/01/17.
 */

public interface ApiUrls {
    String BASE_LOCATION_URL = "http://maps.googleapis.com";
    String BASE_WEATHER_URL = "https://api.darksky.net";


    String GET_LOCATION = "/maps/api/geocode/json";
    String GET_CITY_WEATHER = "/forecast/d8d3e534505a0010e54ef57ea98bcd1c/{LOCATION}";

}
