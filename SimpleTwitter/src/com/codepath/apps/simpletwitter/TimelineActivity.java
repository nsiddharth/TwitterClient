package com.codepath.apps.simpletwitter;

import org.json.JSONObject;
import org.w3c.dom.Text;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.simpletwitter.fragments.HomeTImelineFragment;
import com.codepath.apps.simpletwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletwitter.fragments.TweetsListFragment;
import com.codepath.apps.simpletwitter.listeners.FragmentTabListener;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;


public class TimelineActivity extends FragmentActivity {
	

	private TweetsListFragment tweet_fragment;
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		
		setupTabs();
			
		
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_action_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTImelineFragment>(R.id.flContainer, this, "Home",
								HomeTImelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_action_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "Mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	
	
@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_compose, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public void onProfileClick(MenuItem mi){
		
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
		
	}
	
	public void displayUserTimeline( View v){
		
		String userName="";
		ImageView screenImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		if(screenImage!=null)
			userName = screenImage.getContentDescription().toString();	
		
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("userName", userName);
		startActivity(i);
		
	}
	
	
	public void onCompose(MenuItem mi){
		
		 final AlertDialog.Builder tweetDialog = new AlertDialog.Builder(this);
	        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

	        final View layout = inflater.inflate(R.layout.tweet_dialog,
	                (ViewGroup) findViewById(R.id.layout_root));
	        
	       
	        
	        tweetDialog.setView(layout);
	        tweetDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

	            public void onClick(DialogInterface dialog, int which) {
	            	
	            	EditText text = (EditText) layout.findViewById(R.id.etPostTweet);
	            	
	            	if(text.length() > 140)
	            	{
	            		Toast.makeText(tweetDialog.getContext(), "more than 140 characters", Toast.LENGTH_SHORT).show();
	            	}
	            	else{
	            		
						client.postTweet(new JsonHttpResponseHandler(){
	            			
	            			@Override
	             			public void onSuccess(JSONObject json) {
	            				// TODO Auto-generated method stub
	            				Log.d("debug", json.toString());
	            				super.onSuccess(json);
	            				tweet_fragment.insert(Tweet.fromJSON(json), 0);
	            			}
	            			
	            			@Override
	            			public void onFailure(Throwable arg0, String arg1) {
	            				// TODO Auto-generated method stub
	            				super.onFailure(arg0, arg1);
	            				Log.d("debug", arg0.toString());
	            			}
	            			
	            		}, text.getText().toString());
	            	}
	            	
	                dialog.dismiss();
	            }

	        });


	        tweetDialog.create();
	        tweetDialog.show();     
		
	}

	

	
}

