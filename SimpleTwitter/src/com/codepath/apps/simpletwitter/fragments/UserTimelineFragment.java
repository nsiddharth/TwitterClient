package com.codepath.apps.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	
private TwitterClient client;
private String userName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		client = TwitterApplication.getRestClient();
		
		
		
		
			super.onCreate(savedInstanceState);
	}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
		
		if(this.userName==null)
			populateUserTimeline();
		else
			populateUserTimeline(this.userName);
			super.onActivityCreated(savedInstanceState);
		}
	
	
public void populateUserTimeline(){
		
		client.getUserTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
								Log.d("debug", arg0.toString());
			}
		});
	

}

public void populateUserTimeline(String userName){
	client.getUserTimeline(userName, new JsonHttpResponseHandler(){
		
		@Override
		public void onSuccess(JSONArray json) {
			// TODO Auto-generated method stub
			super.onSuccess(json);
			addAll(Tweet.fromJSONArray(json));
		}
		
		@Override
		public void onFailure(Throwable arg0, String arg1) {
			// TODO Auto-generated method stub
			super.onFailure(arg0, arg1);
							Log.d("debug", arg0.toString());
		}
	});
	
}


public void setUserName(String userName) {
	
	this.userName=userName;
	// TODO Auto-generated method stub	
}

public String getUserName(){
	
	return this.userName;
}

}