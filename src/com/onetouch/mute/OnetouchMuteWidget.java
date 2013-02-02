package com.onetouch.mute;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * The widgetProvider of this Onetouch mute wiget.
 * @author Leo SHANG
 *
 */
public class OnetouchMuteWidget extends AppWidgetProvider {

	private String tag = "OnetouchMuteWidget";
	final public static int REQ_CODE = 0;
	final public static int REQ_MUTE = 1;
	final public static int REQ_UNMUTE = 2;
	public static int LAST_REQ = REQ_UNMUTE; 
	final public static String REQ_NAME = "REQ";
	
	/**
	 * When the an instance of this widget is initialized, set its onClickListener
	 */
	@Override
	public void onEnabled( Context context)
	{
		Log.d(tag, "I'm in onEnabled.");
//		//The onClick action is to launch a new Activity to perform the task
//		Intent intent = new Intent(context, OnetouchMuteActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		//Get the remote view of the widget and set the onClickListener
//		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
//		views.setOnClickPendingIntent(R.id.ibSwitch, pendingIntent);
//		
//		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//		ComponentName cn = new ComponentName(context.getPackageName(), OnetouchMuteWidget.class.getName());
//		appWidgetManager.updateAppWidget(cn, views);
	}
	
	
	@Override
	public void onUpdate (Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds)
	{
		for(int id : appWidgetIds)
		{
			Log.d(tag, "The app widget id is "+id+".");
			//The onClick action is to launch a new Activity to perform the task
			Intent intent = new Intent(context, OnetouchMuteActivity.class);
			
			PendingIntent pendingIntent = PendingIntent.getActivity(context, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			//Get the remote view of the widget and set the onClickListener
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
			views.setOnClickPendingIntent(R.id.ibSwitch, pendingIntent);
			appWidgetManager.updateAppWidget(appWidgetIds, views);
		}
	}
}