package mohammed.hemdan.weather.api.response.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("time")
@Expose
private Double time;
@SerializedName("summary")
@Expose
private String summary;
@SerializedName("icon")
@Expose
private String icon;
@SerializedName("precipIntensity")
@Expose
private Double precipIntensity;
@SerializedName("precipProbability")
@Expose
private Double precipProbability;
@SerializedName("temperature")
@Expose
private Double temperature;
@SerializedName("apparentTemperature")
@Expose
private Double apparentTemperature;
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

public Double getTime() {
return time;
}

public void setTime(Double time) {
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

public Double getPrecipIntensity() {
return precipIntensity;
}

public void setPrecipIntensity(Double precipIntensity) {
this.precipIntensity = precipIntensity;
}

public Double getPrecipProbability() {
return precipProbability;
}

public void setPrecipProbability(Double precipProbability) {
this.precipProbability = precipProbability;
}

public Double getTemperature() {
return temperature;
}

public void setTemperature(Double temperature) {
this.temperature = temperature;
}

public Double getApparentTemperature() {
return apparentTemperature;
}

public void setApparentTemperature(Double apparentTemperature) {
this.apparentTemperature = apparentTemperature;
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