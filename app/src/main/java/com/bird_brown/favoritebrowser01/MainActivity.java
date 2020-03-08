package com.bird_brown.favoritebrowser01;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    private WebView webView; //WebViewクラス
    private SiteOperator operator; //SiteOperatorクラス
    private ArrayList<Site> sites; //サイト格納用配列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBHelperクラスのオブジェクトを生成
        DBHelper helper = new DBHelper(this);

        //SiteOperatorクラスのオブジェクトを生成
        operator = new SiteOperator(helper);

        //お気に入りサイト情報を取得
        sites = operator.getSites();

        //WebViewのオブジェクトを取得
        webView = (WebView)findViewById(R.id.webView1);

        //WebSettingsを取得
        WebSettings settings = webView.getSettings();

        //JavaScriptを有効にする
        settings.setJavaScriptEnabled(true);

        //CustomWebViewClientのオブジェクトを設定
        webView.setWebViewClient(new CustomWebViewClient());

        //CustomWebChromeClientのオブジェクトを設定
        webView.setWebChromeClient(new CustomWebChromeClient());

        if (savedInstanceState == null) {
            //WebViewに表示
            webView.loadUrl("http://www.google.co.jp/");
        } else {
            //savedInstanceStateの状態を復元
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (webView == null) {
            return;
        }

        //webViewの表示を停止
        webView.stopLoading();

        //setWebChromeClientをnullに設定
        webView.setWebChromeClient(null);

        //setWebViewClientをnullに設定
        webView.setWebViewClient(null);

        //webViewを破棄
        webView.destroy();

        //webViewをnullに設定
        webView = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //AlertDialog.Builderクラスのオブジェクトを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //選択したメニュー項目のアイテムIDを取得
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.add_item : //「お気に入りへ追加」を選択
                //ダイアログのタイトルを設定
                builder.setTitle("「お気に入り」への追加");

                //ダイアログのメッセージを設定
                builder.setMessage("このページを「お気に入り」に追加しますか？");

                //ダイアログのOKボタンを設定
                builder.setPositiveButton("OK", this);

                //ダイアログのNOボタンを設定
                builder.setNegativeButton("NO", this);

                //ダイアログを表示
                builder.show();
                break;

            case R.id.select_item : //「お気に入りから選択」を選択
                int i = 0;
                String[]items = new String[sites.size()];

                for (Site site : sites) {
                    items[i++] = site.getTitle();
                }

                if (sites.size() > 0) {
                    //ダイアログのタイトルを設定
                    builder.setTitle("お気に入り一覧");

                    //アイテムを設定
                    builder.setItems(items, this);

                    //ダイアログを表示
                    builder.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String message = "";
        Site site = null;
        ActionBar actionBar = getActionBar();

        switch (which) {
        case DialogInterface.BUTTON_POSITIVE : //「OK」ボタンを押した時
            String title = actionBar.getTitle().toString();
            String url = actionBar.getSubtitle().toString();
            site = new Site(title, url);

            if (operator.addSite(site)) {
                sites = operator.getSites();
                message = "「お気に入り」に登録しました。";
            } else {
                message = "「お気に入り」への登録に失敗しました。";
            }
            break;

        case DialogInterface.BUTTON_NEGATIVE: //「NO」ボタン押した時
            message = "キャンセルしました。";
            break;
        default: //「OK」「NO」ボタン以外の処理
            site = sites.get(which);
            webView.loadUrl(site.getUrl());
            message = site.getTitle();
            break;
        }

        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) { //戻れる場合
            webView.goBack(); //前ページに戻る
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause(); //一時停止
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume(); //再表示
    }
}
