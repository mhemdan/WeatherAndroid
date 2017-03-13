package mohammed.hemdan.weather.ui.weather;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.api.ApiCallBack;
import mohammed.hemdan.weather.api.WeatherApiClient;
import mohammed.hemdan.weather.api.response.weather.WeatherCityResponse;
import mohammed.hemdan.weather.ui.HomeActivity;
import mohammed.hemdan.weather.ui.base.fragment.BaseFragment;
import mohammed.hemdan.weather.util.Constants;
import mohammed.hemdan.weather.util.TempConverter;
import mohammed.hemdan.weather.util.cache.CacheUtil;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by mhemdan on 24/01/17.
 */

public class WeatherForCityFragment extends BaseFragment implements WeatherView {

    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;
    @BindView(R.id.current_weather_status)
    TextView currentWeatherStatus;
    @BindView(R.id.current_weather_temp)
    TextView currentWeatherTemp;

    @BindView(R.id.list)
    RecyclerView daysWeatherList;



    @BindView(R.id.days_choose_container)
    View daysChooseContainer;

    @BindView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;

    private WeatherDaysAdapter adapter;

    private WeatherPresenter presenter;


    public static WeatherForCityFragment getInstance(String location){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CITY_LOCATION,location);
        WeatherForCityFragment fragment = new WeatherForCityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WeatherForCityFragment getInstance(int cityIndex){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CITY_INDEX,cityIndex);
        WeatherForCityFragment fragment = new WeatherForCityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init(){
        setupRecyclerView();
        presenter = new WeatherPresenterImpl(this);
        getWeather();


        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                getWeather();
            }

            @Override
            public void onfinish() {
            }


        });

        if(!CacheUtil.getInstance(getContext()).setWeatherRefreshTutorial())
            new MaterialTapTargetPrompt.Builder(getActivity())
                    .setTarget(((HomeActivity)getActivity()).toolbar)
                    .setBackgroundColour(ContextCompat.getColor(getContext(),(R.color.app_icon_main_color_transparent)))
                    .setPrimaryText(getResources().getString(R.string.app_tutorial_pull_to_refresh_title))
                    .setSecondaryText(getResources().getString(R.string.app_tutorial_pull_to_refresh_message))
                    .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
                    {
                        @Override
                        public void onHidePrompt(MotionEvent event, boolean tappedTarget)
                        {
                            //Do something such as storing a value so that this prompt is never shown again
                        }

                        @Override
                        public void onHidePromptComplete()
                        {

                        }
                    })
                    .show();

    }

    private void getWeather(){
        if(getArguments().containsKey(Constants.CITY_LOCATION)){
            presenter.getWeatherForCity(getArguments().getString(Constants.CITY_LOCATION));
        }else if (getArguments().containsKey(Constants.CITY_INDEX)){

            presenter.getWeatherForCity(
                    CacheUtil.getInstance(getContext()).getAllSavedLocations()
                            .get(
                            getArguments().getInt(Constants.CITY_INDEX)).getGeometry().getLocation());
        }
    }

    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        daysWeatherList.setLayoutManager(linearLayoutManager);
        adapter = new WeatherDaysAdapter(getContext());
        daysWeatherList.setAdapter(adapter);
        daysWeatherList.setHasFixedSize(true);
    }


    @Override
    public void insert(Object object) {
        materialRefreshLayout.finishRefresh();
        WeatherCityResponse response = ((WeatherCityResponse)object);
        currentWeatherStatus.setText(response.getCurrently().getSummary());
        currentWeatherTemp.setText(new TempConverter(CacheUtil.
                getInstance(getContext()).getIsCelsius()).convertTempAccordingToSettings(
                response.getCurrently().getTemperature()));
        try {
            // get input stream

            InputStream ims = getContext().getAssets().open(response.getCurrently().getIcon()
                    .replace("-"," ").replace(" day","").replace(" night","")+".png");

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            currentWeatherIcon.setImageDrawable(d);
        }
        catch(Exception ex) {
            Log.d("ErrorItem",response.getCurrently().getIcon());
            return;
        }
        adapter.insertItems(response.getDaily().getData());
        daysChooseContainer.setVisibility(View.VISIBLE);

        ((HomeActivity)getActivity()).toolbar.setTitle(CacheUtil.getInstance(getContext()).getAllSavedLocations()
                .get(CacheUtil.getInstance(getContext()).getLastOpenedCityIndex()).getAddressComponents().get(0).getShortName());

    }



    @OnClick(R.id.five_days)
    public void onFiveDaysClick(View view){
        adapter.setTenDays(false);
    }

    @OnClick(R.id.ten_days)
    public void onTenDaysClick(View view){
        adapter.setTenDays(true);
    }
}
