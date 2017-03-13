package m.hemdan.stepocrash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by mhemdan on 23/02/17.
 */
public class FileUtil {
    private static FileUtil ourInstance;
    private Context context;
    private int i = 0;
    private File stepoSessionFile;
    private String currentSessionFileName;

    public static FileUtil getInstance(Context context) {
        if(ourInstance != null)
            return ourInstance;
        else
            return ourInstance = new FileUtil(context);
    }

    private FileUtil(Context context) {
        this.context = context;
        this.currentSessionFileName =  String.format("StepoCrash(Session_ %s).txt", String.valueOf(
                Calendar.getInstance().getTimeInMillis()));
    }

    public Uri getCrashStepsFile(){
        return Uri.fromFile(stepoSessionFile);
    }

    public void writeToFile2(String data)
    {
        // Get the directory for the user's public pictures directory.
        i++;
        data += String.format("\n ================================= Step Number %s ===================================== \n",i);

        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
        File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
        File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
        File path =
//                Environment.getExternalStoragePublicDirectory
//                        (
                                //Environment.DIRECTORY_PICTURES
                              new File(sdCard !=null ? sdCard.getAbsolutePath():
                                      externalSdCard.getAbsolutePath()
                                              + "/" + "StepoCrash");
//                        );
        if(!path.exists()){
            path.mkdirs();
        }


        stepoSessionFile = new File(path.getAbsolutePath()
                +"/StepoCrash/",currentSessionFileName
               );
        // Make sure the path directory exists.
        if(!stepoSessionFile.exists())
        {
            // Make it, if it doesn't exit
//            try {
                stepoSessionFile.mkdirs();

//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

//        stepoSessionFile = new File(path,String.format("StepoCrash (Session_ %s).txt", String.valueOf(
//                Calendar.getInstance().getTimeInMillis())));
        // Save your stream, don't forget to flush() it before closing it.

        try
        {
//            stepoSessionFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(stepoSessionFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writeToFile(String data){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        Date now = new Date();
        String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt


        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Report Files");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists())
            {
                root.mkdirs();
            }
            stepoSessionFile = new File(root, fileName);


            FileWriter writer = new FileWriter(stepoSessionFile
                    ,true);
            writer.append(data+"\n\n");
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }
}
