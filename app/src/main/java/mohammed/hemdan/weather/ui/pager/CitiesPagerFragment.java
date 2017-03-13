package mohammed.hemdan.weather.ui.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zplesac.connectionbuddy.ConnectionBuddy;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.ui.HomeActivity;
import mohammed.hemdan.weather.ui.base.fragment.BaseFragment;
import mohammed.hemdan.weather.ui.weather.WeatherForCityFragment;
import mohammed.hemdan.weather.util.Constants;
import mohammed.hemdan.weather.util.cache.CacheUtil;

/**
 * Created by mhemdan on 25/01/17.
 */

public class CitiesPagerFragment extends BaseFragment {


    public static CitiesPagerFragment getInstance(int startIndex){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PAGER_START_INDEX,startIndex);
        CitiesPagerFragment fragment = new CitiesPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.no_internet_connection)
    View noInternetConnectionView;


    private ScreenSlidePagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_cities, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init() {
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        pager.setAdapter(mPagerAdapter);
        pager.setOffscreenPageLimit(CacheUtil.getInstance(getContext()).getAllSavedLocations().size());
        pager.setCurrentItem(getArguments().getInt(Constants.PAGER_START_INDEX));


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((HomeActivity)getActivity()).toolbar.setTitle(CacheUtil.getInstance(getContext()).getAllSavedLocations()
                        .get(position).getAddressComponents().get(0).getShortName());
                CacheUtil.getInstance(getContext()).setLastOpenedCityIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(!ConnectionBuddy.getInstance().hasNetworkConnection()){
            showError(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE);
        }


    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WeatherForCityFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return CacheUtil.getInstance(getContext()).getAllSavedLocations().size();
        }
    }


    @Override
    public void refresh() {
        super.refresh();
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, CitiesPagerFragment.
                getInstance(CacheUtil.getInstance(getContext()).getLastOpenedCityIndex())).commit();
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        if(message.equals(Constants.RETROFIT_NO_INTERNET_ERROR_MESSAGE)){
            noInternetConnectionView.setVisibility(View.VISIBLE);
            pager.setVisibility(View.GONE);
        }
    }

}
