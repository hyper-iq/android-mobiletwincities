package com.recursiveawesome.mtc;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity {

	private static final Void Void = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		new SplashImageTask().execute(Void, Void, Void);
				
	}
	
	private class SplashImageTask extends AsyncTask<Void, Void, Void> {
		boolean noConnectivity = false;

		protected void onPostExecute(Void result) {
			showView();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			loadData();
			return null;
		}
	}
	
	private void showView() {
		Intent mainItent = new Intent(this, MobileTwinCities.class);
		startActivity(mainItent);
		this.finish();
	}
	
	private void loadData() {
		TwitterList.loadTweets();
		//RSSFeedWebsite.loadFeedList();
		RSSFeedGoogleGroup.loadFeedList();
		
	}
}
