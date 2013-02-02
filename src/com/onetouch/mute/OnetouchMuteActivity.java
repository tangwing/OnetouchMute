package com.onetouch.mute;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * This is an invisible Activity which will be launched when
 * the Onetouch widget is pressed. It will preform the real task.
 * @author Leo SHANG
 *
 */
public class OnetouchMuteActivity extends Activity{
	private AudioManager am ;
	private final String TAG = "OnetouchMuteActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//Get the remoteview of the widget, we need to update the button text
		RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.main);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		ComponentName cn = new ComponentName(this.getPackageName(), OnetouchMuteWidget.class.getName());
		
		//Get system audio service
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		//Switch the state according to the last time request
		if(OnetouchMuteWidget.LAST_REQ == OnetouchMuteWidget.REQ_UNMUTE)
		{//To Mute
			try 
			{
				//Save the current volumes to a file, for restoring them later
				FileOutputStream fos = openFileOutput("lastvolumes.txt", 0);
				for(int audioStream = 0; audioStream<=5; audioStream++)
				{
					int volume = am.getStreamVolume(audioStream);
					Log.d(TAG, "Saved: Audio Stream "+audioStream+" = "+volume);
					fos.write(volume);
					
					//Set the current stream volume to 0 (mute)
					am.setStreamVolume(audioStream, 0, 0);//AudioManager.FLAG_SHOW_UI);
				}
				fos.close();
				//Toast.makeText(this, "Mute Mode On", Toast.LENGTH_SHORT).show();
				views.setTextViewText(R.id.ibSwitch, this.getString(R.string.ibSwitch_unmute));
				
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			OnetouchMuteWidget.LAST_REQ = OnetouchMuteWidget.REQ_MUTE;
		}
		else
		{//To Unmute
			try 
			{
				//Restore stream volumes from file
				FileInputStream fis = openFileInput("lastvolumes.txt");
				for(int audioStream = 0; audioStream<=5; audioStream++)
				{
					int index = fis.read();
					Log.d(TAG, "Read: Audio Stream "+audioStream+" = "+index);
					if(am.getStreamVolume(audioStream) == 0 && index > 0 && 
							index <= am.getStreamMaxVolume(audioStream))
						am.setStreamVolume(audioStream, index, 0);// AudioManager.FLAG_SHOW_UI);
				}
				fis.close();
				views.setTextViewText(R.id.ibSwitch, this.getString(R.string.ibSwitch_mute));
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			OnetouchMuteWidget.LAST_REQ = OnetouchMuteWidget.REQ_UNMUTE;
		}
		
		appWidgetManager.updateAppWidget(cn, views);
		Log.d(OnetouchMuteActivity.class.getName(), "onCreate; reqCode = "+OnetouchMuteWidget.LAST_REQ);
		
		//Destroy this activity
		this.finish();
	}
}
