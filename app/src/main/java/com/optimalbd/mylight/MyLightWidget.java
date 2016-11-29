package com.optimalbd.mylight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by ripon on 11/29/2016.
 */

public class MyLightWidget extends AppWidgetProvider {
    private static final String CLICK_NAME_ACTION = "com.optimalbd.mylight.action.widget.click";
    private static RemoteViews rv;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        rv = new RemoteViews(context.getPackageName(), R.layout.widget_option);
        rv.setOnClickPendingIntent(R.id.lightImageView, PendingIntent.getBroadcast(context, 0, new Intent(CLICK_NAME_ACTION), 0));
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean on_off = false;
        super.onReceive(context, intent);
        if (rv == null) {
            rv= new RemoteViews(context.getPackageName(),R.layout.widget_option);
        }

        if (intent.getAction().equals(CLICK_NAME_ACTION)){

            if (Utility.powerOnOff) {
                if (!Utility.powerOnOff) {
                    on_off = true;
                }
                Utility.powerOnOff = on_off;
                rv.setImageViewResource(R.id.lightImageView, R.drawable.poweroff);
                Utility.powerWork.turnOffFlash();
                Utility.powerWork.Destroy();
            } else {
                if (!Utility.powerOnOff) {
                    on_off = true;
                }
                Utility.powerOnOff = on_off;
                rv.setImageViewResource(R.id.lightImageView,R.drawable.poweron);
                Utility.powerWork = new PowerWork();
                Utility.powerWork.turnOnFlash();
            }
        }
        AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
        appWidgetManger.updateAppWidget(appWidgetManger.getAppWidgetIds(new ComponentName(context, MyLightWidget.class)), rv);
    }
}
