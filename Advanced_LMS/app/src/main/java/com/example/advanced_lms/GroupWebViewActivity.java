package com.example.advanced_lms;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GroupWebViewActivity extends AppCompatActivity {
     private WebView mWebView;
     private String myUrl = "http://lms.knu.ac.kr/ilos/m/main/main_form.acl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwebview);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(myUrl);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

}


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

private class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }
    }
}
