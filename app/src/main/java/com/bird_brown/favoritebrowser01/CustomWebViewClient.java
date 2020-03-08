package com.bird_brown.favoritebrowser01;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        //アクティビティを取得
        Activity activity = (Activity)view.getContext();

        //プログレスバーのオブジェクトを取得
        ProgressBar progress = (ProgressBar)activity.findViewById(R.id.progressBar1);

        //プログレスバーを表示
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        //アクティビティを取得
        Activity activity = (Activity)view.getContext();

        //プログレスバーを取得
        ProgressBar progress = (ProgressBar)activity.findViewById(R.id.progressBar1);

        //プログレスバーを非表示
        progress.setVisibility(View.GONE);

        //アクションバーを取得
        //ActionBar action = activity.getActionBar();
        ActionBar action = activity.getActionBar();

        //アクションバーにタイトルを設定
        action.setTitle(view.getTitle());

        //アクションバーのサブタイトルにURLを設定
        action.setSubtitle(url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);

        //アクティビティーを取得
        Activity activity = (Activity)view.getContext();

        //トーストでエラー表示
        Toast t = Toast.makeText(activity, "エラー", Toast.LENGTH_SHORT);
        t.show();
    }
}
