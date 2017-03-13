package mohammed.hemdan.weather.ui;

/**
 * Created by mhemdan on 25/01/17.
 */

public interface HomePresenter {
    void searchForCity(String query);
    void onAttach();
    void onDetach();
}
