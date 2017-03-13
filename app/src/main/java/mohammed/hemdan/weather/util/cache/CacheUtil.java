package mohammed.hemdan.weather.util.cache;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import mohammed.hemdan.weather.api.response.location.Result;
import mohammed.hemdan.weather.util.Constants;
import mohammed.hemdan.weather.util.Util;

/**
 * Created by mhemdan on 25/01/17.
 */
public class CacheUtil {
    private static CacheUtil ourInstance;
    private Context context;

    public static CacheUtil getInstance(Context context) {
        if(ourInstance == null)
            return new CacheUtil(context);
        return ourInstance;
    }

    private CacheUtil(Context context) {
        this.context = context;
    }

    public boolean isThereSavedLocations(){
        return Paper.book().exist(CacheConstants.SAVED_LOCATIONS);
    }
    private void setDefaultLocations(){
        List<Result> defaultLocations = Util.getInstance(context).getDefaultList();
        Paper.book().write(CacheConstants.SAVED_LOCATIONS,defaultLocations);
    }
    public int addLocation(Result result){
        List<Result> savedLocations = getAllSavedLocations();
        int cityIndex;
            if (savedLocations != null) {
                if((cityIndex = isCitySaved(savedLocations,result)) != -1)
                    return cityIndex ;

                savedLocations.add(result);
            } else {
                savedLocations = new ArrayList<>();
                savedLocations.add(result);
            }
        cityIndex = savedLocations.size()-1;
        Paper.book().write(CacheConstants.SAVED_LOCATIONS, savedLocations);
        return cityIndex;

    }



    public int isCitySaved(List<Result> resultList,Result result){

        for( int i =0;i< resultList.size();i++){
            if(resultList.get(i).getPlaceId().equals(result.getPlaceId())){
                return i;
            }
        }
        return -1;
    }
    public List<Result> getAllSavedLocations(){
        if(!Paper.book().exist(CacheConstants.SAVED_LOCATIONS)){
            setDefaultLocations();
        }
        return Paper.book().read(CacheConstants.SAVED_LOCATIONS);
    }
    public void removeLocation(Result result){
        List<Result> savedLocations = getAllSavedLocations();
        if(savedLocations!=null){
            savedLocations.remove(isCitySaved(savedLocations,result));
        }
        Paper.book().write(CacheConstants.SAVED_LOCATIONS,savedLocations);
    }
    public void setIsCelsius(boolean isCelsius){
        Paper.book().write(CacheConstants.IS_CELSIUS,isCelsius);
    }
    public boolean getIsCelsius(){
        return Paper.book().read(CacheConstants.IS_CELSIUS,false);
    }
    public void setLastOpenedCityIndex(int cityIndex){
        Paper.book().write(CacheConstants.LAST_OPENED_CITY_INDEX,cityIndex);
    }
    public int getLastOpenedCityIndex(){
        return Paper.book().read(CacheConstants.LAST_OPENED_CITY_INDEX,0);
    }

    public boolean setWeatherRefreshTutorial(){
        boolean isShown = true;
        if(!Paper.book().exist(CacheConstants.TUTORIAL_WEATHER_PULL_TO_REFRESH)){
            isShown = false;
            Paper.book().write(CacheConstants.TUTORIAL_WEATHER_PULL_TO_REFRESH,true);
        }
        return isShown;
    }

    public boolean setCityRemoveTutorial(){
        boolean isShown = true;
        if(!Paper.book().exist(CacheConstants.TUTORIAL_EDIT_LOCATIONS_REMOVE_CITY)){
            isShown = false;
            Paper.book().write(CacheConstants.TUTORIAL_EDIT_LOCATIONS_REMOVE_CITY,true);
        }
        return isShown;
    }

    public boolean setCityAddTutorial(){
        boolean isShown = true;
        if(!Paper.book().exist(CacheConstants.TUTORIAL_Add_LOCATION_CITY)){
            isShown = false;
            Paper.book().write(CacheConstants.TUTORIAL_Add_LOCATION_CITY,true);
        }
        return isShown;
    }
}
