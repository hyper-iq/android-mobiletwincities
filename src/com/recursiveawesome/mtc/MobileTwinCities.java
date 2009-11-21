package com.recursiveawesome.mtc;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class MobileTwinCities extends TabActivity {
    
	TabHost mTabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		mTabHost = getTabHost();
		
		//this.loadData();
		
		Intent websiteItent = new Intent(this, Website.class);
		websiteItent.putExtra("link", "http://tc-gtug.org/");
		
		Intent twitterFeedList = new Intent(this, TwitterList.class);
		Intent googleGroupRssFeedList = new Intent(this, RSSFeedGoogleGroupList.class);
		
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator(
		"Website").setContent(websiteItent));
		
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Twitter")
				.setContent(twitterFeedList));
		
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Discussion")
				.setContent(googleGroupRssFeedList));
		
	}
	
	private void loadData() {
		TwitterList.loadTweets();
		RSSFeedGoogleGroup.loadFeedList();
	}
}
	
	