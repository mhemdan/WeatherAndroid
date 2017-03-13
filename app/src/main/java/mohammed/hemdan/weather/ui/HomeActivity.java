package mohammed.hemdan.weather.ui;

import android.Manifest;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.api.response.location.LocationCityResponse;
import mohammed.hemdan.weather.api.response.location.Result;
import mohammed.hemdan.weather.ui.base.activity.BaseActivity;
import mohammed.hemdan.weather.ui.location.EditLocationFragment;
import mohammed.hemdan.weather.ui.pager.CitiesPagerFragment;

import mohammed.hemdan.weather.util.cache.CacheUtil;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class HomeActivity extends BaseActivity implements HomeView {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.saved_locations_list)
    RecyclerView savedLocationsList;

    @BindView(R.id.is_celsius)
    SwitchCompat isCelsius;

    private SearchAdapter searchAdapter;

    private LocationsAdapter locationsAdapter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private HomePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();


    }


    private void init() {
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
//        toolbar.setTitle(response.getResults().get(i).getAddressComponents().get(0).getShortName());

        presenter = new HomePresenterImp(this);

        setCurrentCity();
        setupSearch();
        setupNavigationLocationsRecyclerView();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void setCurrentCity() {
        attachWeatherForCityFragment(CacheUtil.getInstance(getBaseContext()).getLastOpenedCityIndex());
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null)
                    search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query != null && query.length() > 2)
                    search(query);
                return false;
            }
        });


    }

    private void setupNavigationLocationsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        savedLocationsList.setLayoutManager(linearLayoutManager);
        locationsAdapter = new LocationsAdapter(this);
        savedLocationsList.setAdapter(locationsAdapter);
        locationsAdapter.insertItems(CacheUtil.getInstance(this).getAllSavedLocations());

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                locationsAdapter.insertItems(CacheUtil.getInstance(HomeActivity.this).getAllSavedLocations());
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        isCelsius.setChecked(CacheUtil.getInstance(getBaseContext()).getIsCelsius());
        isCelsius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CacheUtil.getInstance(getBaseContext()).setIsCelsius(b);
                setCurrentCity();

            }
        });
    }

    private void search(String queryText) {
        presenter.searchForCity(queryText);
    }

    private String[] getLocationNamesFromResultList(List<Result> locations) {
        String[] locationNames;
        if (locations != null && locations.size() > 0) {
            locationNames = new String[locations.size()];
            for (int i = 0; i < locations.size(); i++) {
                locationNames[i] = locations.get(i).getFormattedAddress();
            }


        } else {
            locationNames = new String[1];
            locationNames[0] = getResources().getString(R.string.app_no_result_search);
        }

        return locationNames;
    }


    public void attachWeatherForCityFragment(int index) {

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, CitiesPagerFragment.
                getInstance(index)).commit();
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_location) {
            if (!CacheUtil.getInstance(this).setCityAddTutorial()) {
                new MaterialTapTargetPrompt.Builder(this)
                        .setTarget(findViewById(R.id.add_location_placeholder))
                        .setBackgroundColour(ContextCompat.getColor(this, (R.color.app_icon_main_color_transparent)))
                        .setPrimaryText(getResources().getString(R.string.app_tutorial_add_location_title))
                        .setSecondaryText(getResources().getString(R.string.app_tutorial_add_location_message))
                        .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                            @Override
                            public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                                //Do something such as storing a value so that this prompt is never shown again
                            }

                            @Override
                            public void onHidePromptComplete() {
                                searchView.showSearch();
                            }
                        })
                        .show();
            } else {
                searchView.showSearch();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.edit_locations)
    public void onEditLocationsClick(View view) {
        attachEditLocationsFragment();
    }

    public void attachEditLocationsFragment() {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, EditLocationFragment.
                getInstance()).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void insert(Object object) {
        final LocationCityResponse response = ((LocationCityResponse) object);
        searchAdapter = new SearchAdapter(HomeActivity.this,
                getLocationNamesFromResultList(response.getResults())
        );

        searchView.setAdapter(searchAdapter);
        searchView.showSuggestions();
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if (response.getResults() != null && response.getResults().size() > 0) {
                    int locationIndex;
                    attachWeatherForCityFragment(locationIndex = CacheUtil.getInstance(HomeActivity.this).addLocation(
                            response.getResults().get(i)));
                    CacheUtil.getInstance(HomeActivity.this).setLastOpenedCityIndex(locationIndex);

//                    toolbar.setTitle(response.getResults().get(i).getAddressComponents().get(0).getShortName());
                }
                searchView.closeSearch();
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    public void showError(String message) {
        super.showError(message);
    }

}
