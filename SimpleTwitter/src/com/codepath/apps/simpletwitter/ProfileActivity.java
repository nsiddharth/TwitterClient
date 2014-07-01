package com.codepath.apps.simpletwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.fragments.UserTimelineFragment;
import com.codepath.apps.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	private String userName=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.userName = getIntent().getStringExtra("userName");
		setContentView(R.layout.activity_profile);
		
		UserTimelineFragment userFragment = (UserTimelineFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_user_timeline);
		userFragment.setUserName(userName);
		
		if(this.userName==null)
			loadProfileInfo();
		else
			loadUserInfo();
	}

	private void loadProfileInfo() {
		// TODO Auto-generated method stub
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				User u = User.fromJSON(json);
				
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}	
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
								Log.d("debug", arg0.toString());
			}
		});
	}
	
	private void loadUserInfo() {
		// TODO Auto-generated method stub
		TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				User u = User.fromJSON(json);
			
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}	
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
								Log.d("debug", arg0.toString());
			}
		},this.userName);
	}
	
	private void populateProfileHeader(User u) {
		// TODO Auto-generated method stub
		
		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView)findViewById(R.id.tvcount1);
		TextView tvFollowing = (TextView)findViewById(R.id.tvcount2);
		ImageView profileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(u.getName());
		tvFollowers.setText(String.valueOf(u.getFollowers_count()) + " Followers");
		tvFollowing.setText(String.valueOf(u.getFollowing_count()) + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageURL(), profileImage);
		tvTagline.setText(u.getDescription());
		
		
	}
	
}
