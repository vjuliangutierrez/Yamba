package co.edu.udea.cmovil.gr1.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
    static final String TAG = "RefreshService";

    public RefreshService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onStarted");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = prefs.getString("usuario", "");
        final String password = prefs.getString("contraseña", "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Log.e(TAG, "Por favor actualiza tu usuario y contraseña");
            return;
        }

        YambaClient cloud = new YambaClient(username, password);
        try {
            List<Status> timeline = cloud.getTimeline(20);
            for (Status status : timeline) {
                Log.d(TAG, String.format("%s: %s", status.getUser(), status.getMessage()));
            }
        } catch (YambaClientException e) { //
            Log.e(TAG, "Fallo al obtener actualizacion de estados", e);
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }
}
