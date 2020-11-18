package com.example.donaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsitesActivity extends AppCompatActivity {
    public String url;
    public WebView webviiew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_websites);
        webviiew = (WebView) findViewById(R.id.webView);
        webviiew.setWebViewClient(new WebViewClient());
        webviiew.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webviiew.canGoBack()){
            webviiew.goBack();
        }else {
            super.onBackPressed();
        }
    }
}