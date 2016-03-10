package com.uic.sandeep.phonepark.classification;

import android.annotation.SuppressLint;
import android.content.Context;

import com.uic.sandeep.phonepark.Constants;
import com.uic.sandeep.phonepark.indicator.accelerometerbased.AccelerometerFeatureExtraction;
import com.uic.sandeep.phonepark.indicator.accelerometerbased.AccelerometerFeatureExtraction.Config;

import java.util.HashMap;


/**
 * 
 * @author Shuo
 * A skeleton class that classifies the sensor readings
 */
public class ClassificationManager {
	public static final String LOG_TAG=ClassificationManager.class.getCanonicalName();
	
	//all the classifiers that deals with the sensor readings
	public HashMap<Integer, WekaClassifier> mClassfiers;
	
	public AccelerometerFeatureExtraction mCIVFeatureExtraction;
	public AccelerometerFeatureExtraction mMSTFeatureExtraction;
	
	private Context mContext;
	
	@SuppressLint("UseSparseArrays")
	private ClassificationManager(Context ctxt){
		mClassfiers=new HashMap<Integer, WekaClassifier>();
		
		mContext=ctxt;
		
		//TODO parameters to be adjusted
		mCIVFeatureExtraction=new AccelerometerFeatureExtraction(new Config(10, 3));
		mMSTFeatureExtraction=new AccelerometerFeatureExtraction(new Config(5, 5));	
		
		int n= Constants.CLASSIFIER_NAME.length;
		int classifierName;
		for(int i=0;i<n;i++){
			classifierName=Constants.CLASSIFIER_NAME[i];
			String filterName=Constants.CLASSIFIER_FILTER_NAME[i];
			String filterOptions=Constants.CLASSIFIER_FILTER_OPTION[i];
			HashMap<String, Integer> classierfierClsToEvents=new HashMap<String, Integer>();

			for(int j=0;j<Constants.CLASSIFIER_CLASS[i].length;j++){
				classierfierClsToEvents.put(Constants.CLASSIFIER_CLASS[i][j], Constants.CLASSIFIER_EVENT[i][j]);
			}
			
			mClassfiers.put(classifierName, new WekaClassifier(mContext, classifierName, filterName, filterOptions, classierfierClsToEvents));			
		}
	}

	
	//get the instance
	public static ClassificationManager mClassificationInstance;
	public static ClassificationManager getInstance(Context ctxt){
		if(mClassificationInstance==null){
			mClassificationInstance=new ClassificationManager(ctxt);
		}
		return mClassificationInstance;
	}
	
	
	

}
