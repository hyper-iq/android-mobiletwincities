package com.recursiveawesome.mtc;

import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RSSFeedGoogleGroupList extends ListActivity implements OnClickListener {

	private static List<RSSFeedGoogleGroup> feedItems = RSSFeedGoogleGroup.feedItems;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iterator<RSSFeedGoogleGroup> fi = feedItems.iterator();
        
        String[]dd = new String [feedItems.size()]; 
        
        int i = 0;
        while(fi.hasNext()) {
        	RSSFeedGoogleGroup rss = (RSSFeedGoogleGroup)fi.next();
        	dd[i] = rss.getTitle();
        	i++;
        }
        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dd));
        getListView().setTextFilterEnabled(true);
        
    }
	
	protected void onListItemClick(ListView l, View v,
            int position, long id) {
		String url = feedItems.get(position).getLink();
		Intent websiteItent = new Intent(this, Website.class);
		websiteItent.putExtra("link", url);
		startActivity(websiteItent);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
