package com.recursiveawesome.mtc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class TwitterList extends ListActivity implements OnItemClickListener, Runnable {

	final public static String TWEET = "TWEET";
	final public static String DATE = "DATE";
	private static ArrayList<Map<String, String>> list;

	private ProgressDialog pd;
	SimpleAdapter sa;
	
	// Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);

		TextView tv = (TextView) findViewById(R.id.news_title);
		tv.setText("@MobileTC on Twitter");

		
		sa = new SimpleAdapter(this, list, R.layout.twitter_item_row,
				new String[] { TWEET, DATE }, new int[] {
						R.id.news_item_row_text, R.id.news_item_row_when });
		setListAdapter(sa);
		
		getListView().setOnItemClickListener(this);
		mHandler.post(mUpdateResults);
	}
	
	public static void loadTweets() {
		
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
			callTwitter();
			//pd = ProgressDialog.show(this, "Working...", "Downloading Tweets for MobileTC ", true, true);
			//Thread thread = new Thread(this);
			//thread.start();
		}
	}
	
	// Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };

    private void updateResultsInUi() {
    	sa.notifyDataSetChanged();
    	//pd.dismiss();
    }
    
    private static void callTwitter() {
    	
    	try {
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			
			HttpGet httpGet = new HttpGet("http://twitter.com/statuses/user_timeline.json?id=mobiletc");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(httpGet, responseHandler);
			Log.d("APIRequest:GetRequest", response);
			
			JSONArray jsonResults = new JSONArray(response);
			for (int i = 0; i < jsonResults.length(); i++) {
				JSONObject obj = (JSONObject) jsonResults.get(i);
				String text = obj.getString("text");
				String when = obj.getString("created_at");

				DateFormat formatter = new SimpleDateFormat(
						"E MMM dd HH:mm:ss Z yyyy");
				Date date = (Date) formatter.parse(when);
				Map<String, String> map = new HashMap<String, String>();
				map.put(TWEET, text);
				map.put(DATE, timeSinceNow(date));
				list.add(map);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	public void run() {
		callTwitter();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		Map m = list.get(position);
		String tweet = (String) m.get(TWEET);
		int start = tweet.indexOf("http://");
		int end = tweet.indexOf(" ", start);
		if (end == -1) {
			end = tweet.length();
		}
		if (start > -1) {
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tweet
					.substring(start, end)));
			startActivity(i);
		}
	}

	private static String timeSinceNow(Date d) {
		long now = new Date().getTime();
		long dateInQuestion = d.getTime();

		long ti = (now - dateInQuestion) / 1000;

		if (ti < 1) {
			return "never";
		} else if (ti < 60) {
			return "less than a minute ago";
		} else if (ti < 3600) {
			int diff = Math.round(ti / 60);
			return diff + " minutes ago";
		} else if (ti < 86400) {
			int diff = Math.round(ti / 60 / 60);
			return diff + " hours ago";
		} else {
			int diff = Math.round(ti / 60 / 60 / 24);
			return diff + " days ago";
		}
	}
}