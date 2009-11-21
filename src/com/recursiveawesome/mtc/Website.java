package com.recursiveawesome.mtc;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Website extends Activity {
	
	WebView webView;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.website);

		String link = this.getIntent().getStringExtra("link");
			
		webView = (WebView) findViewById(R.id.web_view);
		//webView.setWebViewClient(new HelloWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://mobiletwincities.com");

	}
	/*
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	        webView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	*/
	

}
