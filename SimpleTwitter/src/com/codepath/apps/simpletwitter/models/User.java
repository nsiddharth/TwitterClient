package com.codepath.apps.simpletwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String name;
	private long uid;
	private String screenName;
	private String profileImageURL;
	private long followers_count;
	private long following_count;
	private String description;
	public static User fromJSON(JSONObject json){
		User u = new User();
		
		try{
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageURL = json.getString("profile_image_url");
			u.followers_count = json.getLong("followers_count");
			u.following_count = json.getLong("friends_count");
			u.description = json.getString("description");
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public long getFollowers_count() {
		return followers_count;
	}

	

	public long getFollowing_count() {
		return following_count;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
