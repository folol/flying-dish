package com.example.devansh.flyingdish;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

/**
 * Created by devansh on 9/22/16.
 */
public class FlyingWidgetProvider extends AppWidgetProvider implements Animation.AnimationListener{



    private static final String SYNC_CLICKED    = "automaticWidgetAnimButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.flying_widget);
        watchWidget = new ComponentName(context, FlyingWidgetProvider.class);

        remoteViews.setOnClickPendingIntent(R.id.anim_show, getPendingSelfIntent(context, SYNC_CLICKED));


        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        Log.d("Flyingdish", "onrec");

        if (SYNC_CLICKED.equals(intent.getAction())) {

            Log.d("Flyingdish", "sync clicked");

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.flying_widget);
            watchWidget = new ComponentName(context, FlyingWidgetProvider.class);

            remoteViews.setTextViewText(R.id.widgetName, "TESTING");

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Intent intent1 = new Intent(context, AnimationService.class);
                context.startService(intent1);
            }
            else {
                Intent intent1 = new Intent(context , ShowAnimation.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }


        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
       // Intent intent = new Intent(context, ShowAnimation.class);
        Intent intent = new Intent(context , getClass());
        intent.setAction(action);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //return PendingIntent.getActivity(context, 0, intent, 0);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {


    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
