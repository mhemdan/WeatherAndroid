package mohammed.hemdan.weather.ui.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.ui.HomeActivity;
import mohammed.hemdan.weather.ui.base.activity.BaseActivity;
import mohammed.hemdan.weather.ui.base.fragment.BaseFragment;
import mohammed.hemdan.weather.util.cache.CacheUtil;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by mhemdan on 24/01/17.
 */

public class EditLocationFragment extends BaseFragment{
    @BindView(R.id.saved_locations_list)
    RecyclerView savedLocationList;
    private SavedLocationsAdapter adapter;

    public static EditLocationFragment getInstance(){
        return new EditLocationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_location, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init(){
        ((HomeActivity)getActivity()).toolbar.setTitle(R.string.app_edit_locations);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        savedLocationList.setLayoutManager(linearLayoutManager);
        adapter = new SavedLocationsAdapter(getContext());
        savedLocationList.setAdapter(adapter);
        adapter.insertItems(CacheUtil.getInstance(getContext()).getAllSavedLocations());
        if(!CacheUtil.getInstance(getContext()).setCityRemoveTutorial())
            savedLocationList.postDelayed(new Runnable() {
            @Override
            public void run() {
                new MaterialTapTargetPrompt.Builder(getActivity())
                        .setTarget(savedLocationList.getChildAt(0))
                        .setBackgroundColour(ContextCompat.getColor(getContext(),(R.color.app_icon_main_color_transparent)))
                        .setPrimaryText(getResources().getString(R.string.app_tutorial_swipe_to_remove_title))
                        .setSecondaryText(getResources().getString(R.string.app_tutorial_swipe_to_remove_message))
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
        },1000);

    }
}
