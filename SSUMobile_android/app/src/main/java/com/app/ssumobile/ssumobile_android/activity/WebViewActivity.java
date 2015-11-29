package com.app.ssumobile.ssumobile_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;


public class WebViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(webView);
        // Get the url from intent and save in bundle;
        Bundle B = getIntent().getExtras();

        // set url string from Bundle
        String url = (String) B.get("url");

        // Check to make sure the url has "http" in it, or won't render page
        String isHttp = url.substring(0,3);
        if( !isHttp.equals("http") )
            url = "http://" + url;

        // Display the url to the screen to make sure it's correct.
        Toast.makeText(getBaseContext(), url, Toast.LENGTH_SHORT).show();

        // Set a web client to open all links in the same webview
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        //
        webView.getSettings().setLoadWithOverviewMode(true);

        // Fit content within screen size.
        webView.getSettings().setUseWideViewPort(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        // Don't allow the scroll bar to fade away
        webView.setScrollbarFadingEnabled(false);

        // loads the webView completely zoomed out
        webView.getSettings().setJavaScriptEnabled(true);

        // Enable Java Script
        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.loadUrl(url);
    }
}
