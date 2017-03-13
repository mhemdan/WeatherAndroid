package mohammed.hemdan.weather.api.response.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Datum_ {

@SerializedName("time")
@Expose
private Long time;
@SerializedName("summary")
@Expose
private String summary;
@SerializedName("icon")
@Expose
private String icon;
@SerializedName("sunriseTime")
@Expose
private Double sunriseTime;
@SerializedName("sunsetTime")
@Expose
private Double sunsetTime;
@SerializedName("moonPhase")
@Expose
private Double moonPhase;
@SerializedName("precipIntensity")
@Expose
private Double precipIntensity;
@SerializedName("precipIntensityMax")
@Expose
private Double precipIntensityMax;
@SerializedName("precipProbability")
@Expose
private Double precipProbability;
@SerializedName("temperatureMin")
@Expose
private Double temperatureMin;
@SerializedName("temperatureMinTime")
@Expose
private Double temperatureMinTime;
@SerializedName("temperatureMax")
@Expose
private Double temperatureMax;
@SerializedName("temperatureMaxTime")
@Expose
private Double temperatureMaxTime;
@SerializedName("apparentTemperatureMin")
@Expose
private Double apparentTemperatureMin;
@SerializedName("apparentTemperatureMinTime")
@Expose
private Double apparentTemperatureMinTime;
@SerializedName("apparentTemperatureMax")
@Expose
private Double apparentTemperatureMax;
@SerializedName("apparentTemperatureMaxTime")
@Expose
private Double apparentTemperatureMaxTime;
@SerializedName("dewPoint")
@Expose
private Double dewPoint;
@SerializedName("humidity")
@Expose
private Double humidity;
@SerializedName("windSpeed")
@Expose
private Double windSpeed;
@SerializedName("windBearing")
@Expose
private Double windBearing;
@SerializedName("cloudCover")
@Expose
private Double cloudCover;
@SerializedName("pressure")
@Expose
private Double pressure;
@SerializedName("ozone")
@Expose
private Double ozone;

public Long getTime() {
return time;
}
public String getDayName(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis((time * 1000));
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
    return dayFormat.format(calendar.getTime());
}
public void setTime(Long time) {
this.time = time;
}

public String getSummary() {
return summary;
}

public void setSummary(String summary) {
this.summary = summary;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

public Double getSunriseTime() {
return sunriseTime;
}

public void setSunriseTime(Double sunriseTime) {
this.sunriseTime = sunriseTime;
}

public Double getSunsetTime() {
return sunsetTime;
}

public void setSunsetTime(Double sunsetTime) {
this.sunsetTime = sunsetTime;
}

public Double getMoonPhase() {
return moonPhase;
}

public void setMoonPhase(Double moonPhase) {
this.moonPhase = moonPhase;
}

public Double getPrecipIntensity() {
return precipIntensity;
}

public void setPrecipIntensity(Double precipIntensity) {
this.precipIntensity = precipIntensity;
}

public Double getPrecipIntensityMax() {
return precipIntensityMax;
}

public void setPrecipIntensityMax(Double precipIntensityMax) {
this.precipIntensityMax = precipIntensityMax;
}

public Double getPrecipProbability() {
return precipProbability;
}

public void setPrecipProbability(Double precipProbability) {
this.precipProbability = precipProbability;
}

public Double getTemperatureMin() {
return temperatureMin;
}

public void setTemperatureMin(Double temperatureMin) {
this.temperatureMin = temperatureMin;
}

public Double getTemperatureMinTime() {
return temperatureMinTime;
}

public void setTemperatureMinTime(Double temperatureMinTime) {
this.temperatureMinTime = temperatureMinTime;
}

public Double getTemperatureMax() {
return temperatureMax;
}

public void setTemperatureMax(Double temperatureMax) {
this.temperatureMax = temperatureMax;
}

public Double getTemperatureMaxTime() {
return temperatureMaxTime;
}

public void setTemperatureMaxTime(Double temperatureMaxTime) {
this.temperatureMaxTime = temperatureMaxTime;
}

public Double getApparentTemperatureMin() {
return apparentTemperatureMin;
}

public void setApparentTemperatureMin(Double apparentTemperatureMin) {
this.apparentTemperatureMin = apparentTemperatureMin;
}

public Double getApparentTemperatureMinTime() {
return apparentTemperatureMinTime;
}

public void setApparentTemperatureMinTime(Double apparentTemperatureMinTime) {
this.apparentTemperatureMinTime = apparentTemperatureMinTime;
}

public Double getApparentTemperatureMax() {
return apparentTemperatureMax;
}

public void setApparentTemperatureMax(Double apparentTemperatureMax) {
this.apparentTemperatureMax = apparentTemperatureMax;
}

public Double getApparentTemperatureMaxTime() {
return apparentTemperatureMaxTime;
}

public void setApparentTemperatureMaxTime(Double apparentTemperatureMaxTime) {
this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
}

public Double getDewPoint() {
return dewPoint;
}

public void setDewPoint(Double dewPoint) {
this.dewPoint = dewPoint;
}

public Double getHumidity() {
return humidity;
}

public void setHumidity(Double humidity) {
this.humidity = humidity;
}

public Double getWindSpeed() {
return windSpeed;
}

public void setWindSpeed(Double windSpeed) {
this.windSpeed = windSpeed;
}

public Double getWindBearing() {
return windBearing;
}

public void setWindBearing(Double windBearing) {
this.windBearing = windBearing;
}

public Double getCloudCover() {
return cloudCover;
}

public void setCloudCover(Double cloudCover) {
this.cloudCover = cloudCover;
}

public Double getPressure() {
return pressure;
}

public void setPressure(Double pressure) {
this.pressure = pressure;
}

public Double getOzone() {
return ozone;
}

public void setOzone(Double ozone) {
this.ozone = ozone;
}

}