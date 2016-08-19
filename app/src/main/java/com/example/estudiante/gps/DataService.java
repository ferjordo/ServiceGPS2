package com.example.estudiante.gps;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by estudiante on 18/08/16.
 */


public class DataService extends  WakefulIntentService {
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
//
//        String Data = intent.getDataString();
//        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                AlarmManager.INTERVAL_HALF_HOUR,
//                AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);

        TasksDataSource db = TasksDataSource.getInstance(this); //get access to the instance of TasksDataSource
        TaskAlarm alarm = new TaskAlarm();

        List<Task> tasks = db.getAllTasks(); //Get a list of all the tasks there
        for (Task task : tasks) {
            // Cancel existing alarm
            alarm.cancelAlarm(this, task.getID());

            //Procrastinator and Reminder alarm
            if(task.isPastDue()){
                alarm.setReminder(this, task.getID());
            }

            //handle repeat alarms
            if(task.isRepeating() && task.isCompleted()){
                task = alarm.setRepeatingAlarm(this, task.getID());
            }

            //regular alarms
            if(!task.isCompleted() && (task.getDateDue() >= System.currentTimeMillis())){
                alarm.setAlarm(this, task);
            }
        }
        super.onHandleIntent(intent);

    }
}
