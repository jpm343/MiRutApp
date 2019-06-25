package com.example.mirutapp.Services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

public class ResetRoutesAlarmAtBootReceiver extends BroadcastReceiver {
    public static final String TAG = "ResetRoutesAlarmAtBootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            //start routes alarm setter job service with extra in order to reset all the routes' alarms
            PersistableBundle bundle = new PersistableBundle();
            bundle.putString("ResetAlarms", "true");
            ComponentName componentName = new ComponentName(context, RoutesAlarmSetterJobService.class);
            JobInfo info = new JobInfo.Builder(2, componentName)
                    .setOverrideDeadline(0)
                    .setExtras(bundle)
                    .setPersisted(true)
                    .build();

            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(info);
            if(resultCode == JobScheduler.RESULT_SUCCESS)
                Log.d(TAG, "Job scheduled from boot receiver");
        }
    }
}
