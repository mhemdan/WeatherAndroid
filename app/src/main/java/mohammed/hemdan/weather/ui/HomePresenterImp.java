package mohammed.hemdan.weather.ui;


import mohammed.hemdan.weather.api.ApiCallBack;
import mohammed.hemdan.weather.api.LocationApiClient;

/**
 * Created by mhemdan on 25/01/17.
 */

public class HomePresenterImp implements HomePresenter, ApiCallBack {
    private HomeView view;
    private LocationApiClient apiClient;
    public HomePresenterImp(HomeView view){
        this.view = view;
        this.apiClient = LocationApiClient.getInstance();
    }
    @Override
    public void searchForCity(String query) {
        this.apiClient.getCityAndLocationByWord(query,this);
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSuccess(Object object) {
        this.view.insert(object);
    }

    @Override
    public void onFailure(Object object) {
        view.showError(object.toString());
    }
}
