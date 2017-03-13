package m.hemdan.stepocrash;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;

/**
 * Created by mhemdan on 23/02/17.
 */
public class StepoCrash {
    private static StepoCrash ourInstance;
    private Context context;
    private SharedPreferences stepoPreference;
    private static FileUtil fileUtil;

    public static StepoCrash init(Context context) {
        if(ourInstance!=null)
        return ourInstance;
        else
            return ourInstance = new StepoCrash(context);
    }

    private StepoCrash( Context context) {
        this.context = context;
        fileUtil = FileUtil.getInstance(context);

        stepoPreference =
                context.getSharedPreferences(Constants.STEPO_CRASH_PREFERENCE,Context.MODE_PRIVATE);

//        if(!stepoPreference.getString(Constants.IS_CRASH,"1").equals("1")){

//            stepoPreference.edit().putString(Constants.IS_CRASH,"1");
//            stepoPreference.edit().commit();
//        }

        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e) {
                addStep(Log.getStackTraceString(e));
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://stepocrash.appspot.com");
                UploadTask uploadTask = storageRef.putFile(FileUtil.getInstance(StepoCrash.this.context).getCrashStepsFile());
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d("firebase",exception.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d("firebase",downloadUrl.toString());
                    }
                });
                stepoPreference.edit().putString(Constants.IS_CRASH,"sadas");
                stepoPreference.edit().commit();
            }
        });

    }
    public static void addStep(String tag,String step){
        if(fileUtil!=null) {
            step = "#" + tag + "#" + step;
            fileUtil.writeToFile(step);
        }
    }

    public static void addStep(String step){
        if(fileUtil!=null)
            fileUtil.writeToFile(step);
    }
    public static void addStep(Object step){
        if(fileUtil!=null)
            fileUtil.writeToFile(new Gson().toJson(step));
    }
    public static void addStep(String tag,Object step){
        if(fileUtil!=null) {
            String stepString = "#" + tag + "#" + new Gson().toJson(step);
            fileUtil.writeToFile(stepString);
        }
    }

}
