package mohammed.hemdan.weather.ui.location;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.api.response.location.Result;
import mohammed.hemdan.weather.ui.HomeActivity;
import mohammed.hemdan.weather.ui.base.activity.BaseActivity;
import mohammed.hemdan.weather.util.cache.CacheUtil;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by mhemdan on 25/01/17.
 */

public class SavedLocationsAdapter extends RecyclerView.Adapter<SavedLocationsAdapter.LocationViewHolder>{
    private List<Result> items;
    private Context context;
    public SavedLocationsAdapter(Context context){
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void insertItems(List<Result> newItems){
        if(newItems!=null) {
            this.items.clear();
            this.items.addAll(newItems);
            this.notifyDataSetChanged();
            this.notifyItemRangeChanged(0, items.size());
            this.notifyItemRangeInserted(0, items.size());
        }
    }

    public void clearWithNotify(){
        int size = items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup locationView = ( ViewGroup ) mInflater.inflate ( R.layout.card_edit_location, parent, false );
        LocationViewHolder viewHolder = new LocationViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        holder.locationName.setText(items.get(position).getFormattedAddress());
        holder.cityDetailsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)context).attachWeatherForCityFragment(position);
                ((HomeActivity)context).toolbar.setTitle(items.get(position).
                        getAddressComponents().get(0).getShortName());
                CacheUtil.getInstance(context).setLastOpenedCityIndex(position);

            }
        });
        holder.cityName.setText(items.get(position).getAddressComponents().get(0).getShortName());
        if(items.size()==1){
            holder.swipeLayout.setSwipeEnabled(false);
        }
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheUtil.getInstance(context).removeLocation(items.get(position));
                items.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.location_name)
        TextView locationName;
        @BindView(R.id.city_name)
        TextView cityName;
        @BindView(R.id.trash)
        ImageView trash;
        @BindView(R.id.swipe_layout)
        SwipeLayout swipeLayout;

        @BindView(R.id.city_details_container)
        LinearLayout cityDetailsContainer;

        public LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
