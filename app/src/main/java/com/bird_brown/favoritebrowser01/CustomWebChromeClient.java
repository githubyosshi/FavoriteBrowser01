package com.bird_brown.favoritebrowser01;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class CustomWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

        //アクティビティを取得
        Activity activity = (Activity)view.getContext();

        //プログレスバーを取得
        ProgressBar bar = (ProgressBar)activity.findViewById(R.id.progressBar1);

        //プログレスバーに現在値を設定
        bar.setProgress(newProgress);
    }
}
