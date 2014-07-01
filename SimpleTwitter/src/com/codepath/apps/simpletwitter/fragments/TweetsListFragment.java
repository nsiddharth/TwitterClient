package com.codepath.apps.simpletwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.simpletwitter.EndlessScrollListener;
import com.codepath.apps.simpletwitter.R;
import com.codepath.apps.simpletwitter.TweetArrayAdapter;
import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {

	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private TwitterClient client;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
			
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				
				loadMoreTweets(page);
			}
		});
		
		
		return v;
		
	}
	
	
	public void loadMoreTweets(int page){
		//int nextPage = page*20;
		//String since_id = String.valueOf(nextPage);
		
		Tweet last_tweet=tweets.get(tweets.size()-1);
		String last_id = String.valueOf(last_tweet.getUid());
		
		populateTimeline(last_id);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(),  tweets);
		client = TwitterApplication.getRestClient();
		//populateTimeline("0");
		//setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	
	public void addAll(ArrayList<Tweet> tweets ){
		
		aTweets.addAll(tweets);;
	}
	
	
	
	public void populateTimeline(String max_id){
		
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
								Log.d("debug", arg0.toString());
			}
		},  max_id, 10);
		
	}

	
	public void insert(Tweet tweet, int position){
		aTweets.insert(tweet, position);
		
	}
	
	

}
