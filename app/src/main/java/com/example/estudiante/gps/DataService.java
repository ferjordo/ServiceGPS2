package com.example.estudiante.gps;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by estudiante on 18/08/16.
 */


public class DataService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public DataService(String name) {
        super(name);



    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String Data = intent.getDataString();
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
    }
}
