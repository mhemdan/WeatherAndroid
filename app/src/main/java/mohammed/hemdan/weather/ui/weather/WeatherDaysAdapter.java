package mohammed.hemdan.weather.ui.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mohammed.hemdan.weather.R;
import mohammed.hemdan.weather.api.response.weather.Datum_;
import mohammed.hemdan.weather.util.Constants;
import mohammed.hemdan.weather.util.TempConverter;
import mohammed.hemdan.weather.util.cache.CacheUtil;

/**
 * Created by mhemdan on 24/01/17.
 */

public class WeatherDaysAdapter extends RecyclerView.Adapter<WeatherDaysAdapter.WeatherDayViewHolder> {

    private List<Datum_> items;
    private Context context;
    private boolean isTenDays = false;

    public WeatherDaysAdapter(Context context){
        this.context = context;
        this.items = new ArrayList<>();
    }
    public void insertItems(List<Datum_> newItems){
        items.clear();
        this.items.addAll(newItems);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(0, items.size());
        this.notifyItemRangeInserted(0,items.size());
    }

    public void clearWithNotify(){
        int size = items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, size);
    }
    @Override
    public WeatherDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup dayWeatherView = ( ViewGroup ) mInflater.inflate ( R.layout.card_day_weather, parent, false );
        WeatherDayViewHolder viewHolder = new WeatherDayViewHolder(dayWeatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherDayViewHolder holder, int position) {
        holder.dayName.setText(items.get(position).getDayName());
        holder.maxTemp.setText(new TempConverter(CacheUtil.
                getInstance(context).getIsCelsius()).convertTempAccordingToSettings(items.
                get(position).getTemperatureMax()));
        holder.minTemp.setText(new TempConverter(CacheUtil.
                getInstance(context).getIsCelsius()).convertTempAccordingToSettings(
                items.get(position).getTemperatureMin()));
        try {
            // get input stream

            InputStream ims = context.getAssets().open(items.
                    get(position).getIcon()
                    .replace("-"," ").replace(" day","").replace(" night","")+".png");

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            holder.dayWeatherIcon.setImageDrawable(d);
        }
        catch(IOException ex) {
            Log.d("ErrorItem",items.get(position).getIcon());
            return;
        }
    }

    public void setTenDays(boolean tenDays) {
        isTenDays = tenDays;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(!isTenDays)
            if(items.size()>5)
                return 5;
        return items.size();
    }

    public static class WeatherDayViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.day_name)
        TextView dayName;
        @BindView(R.id.min_temp)
        TextView minTemp;
        @BindView(R.id.max_temp)
        TextView maxTemp;
        @BindView(R.id.day_weather_icon)
        ImageView dayWeatherIcon;
        public WeatherDayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
