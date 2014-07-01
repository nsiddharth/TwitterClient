package com.codepath.apps.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTImelineFragment extends TweetsListFragment {

private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		client = TwitterApplication.getRestClient();
		
		populateTimeline("0");
		super.onCreate(savedInstanceState);
	}
	
public void populateTimeline(String max_id){
		
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			
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
		},  max_id, 10);
		
	}
}
