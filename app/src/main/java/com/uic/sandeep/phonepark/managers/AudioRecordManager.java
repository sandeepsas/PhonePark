package com.uic.sandeep.phonepark.managers;

import android.util.Log;

import com.uic.sandeep.phonepark.Constants;

import java.util.Timer;
import java.util.TimerTask;

public class AudioRecordManager {
	private static final String LOG_TAG = AudioRecordManager.class.getCanonicalName();
	 
	
	private static AudioRecordManager mAudioRecordManager;
	private AudioRecordManager(){
	}
	
	public static AudioRecordManager getInstance(){
		if(mAudioRecordManager==null){
			mAudioRecordManager=new AudioRecordManager();
		}
		return mAudioRecordManager;
	}
	
	
	private ExtAudioRecorder audioRecorder;
	
	//private AudioRecord audioRecorder;
	
	public void recordAudioSample(String outputFileName){
		//start recording
		startRecording(outputFileName);
		
		//stop recording
		Timer timer=new Timer();
		TimerTask stopRecording=new TimerTask() {
			@Override
			public void run() {
				stopRecording();
			}
		};
		timer.schedule(stopRecording, Constants.AUDIO_SAMPLE_DURATION_IN_SECONDS*1000);
			
	}
	
	private void startRecording(String outputFileName){
		// Start recording
		//extAudioRecorder = ExtAudioRecorder.getInstanse(true);	  // Compressed recording (AMR)
		audioRecorder = ExtAudioRecorder.getInstanse(false); // Uncompressed recording (WAV)
		audioRecorder.setOutputFile(outputFileName);
		audioRecorder.prepare();
		audioRecorder.start();
		Log.e(LOG_TAG, "start records");
	}
	
	private void stopRecording(){
		// Stop recording
		audioRecorder.stop();
		audioRecorder.release();
		Log.e(LOG_TAG, "stop records");
	}
	
	
	
	
	

      
}
