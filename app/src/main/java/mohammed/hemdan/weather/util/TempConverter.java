package mohammed.hemdan.weather.util;

import java.text.DecimalFormat;

public class TempConverter {

    private boolean isCelsius = false;
    public TempConverter(boolean isCelsius){
        this.isCelsius = isCelsius;
    }
    public String convertTempAccordingToSettings(Double temp){
        if(isCelsius){
           return new DecimalFormat("#.##").
                   format(convertFtoCAndPrint(temp))+" " +Constants.DEGREE_CHAR + "C";
        }else {
           return temp +" " +Constants.DEGREE_CHAR + "F";
        }
    }

    public Double convertFtoCAndPrint(Double temp) {
        return ((5* (temp - 32)) / 9f);
    }

    public Double convertCtoFAndPrint(Double temp) {
        return (9 * temp + 160) / 5f;
    }
}