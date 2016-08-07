package com.pierce.webviewhttpsdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static android.app.AlertDialog.Builder;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSettings();
        initData();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.wv_content);
    }

    private void initSettings() {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private void initData() {
        //https://developer.apple.com/
        //mWebView.loadUrl("https://m.taobao.com/");
        //加载本地的html
        mWebView.loadUrl("file:///android_asset/neterror.html");
        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "obj");
    }


    //改写物理按键,返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //handler.cancel(); // Android默认的处理方式
            handler.proceed();  // 接受所有网站的证书
            //handleMessage(Message msg); // 进行其他处理
        }
    }

    private class MyJavascriptInterface {

        @android.webkit.JavascriptInterface
        public void androidMethod() {

            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "JavaScript调用了Android中的方法", Toast.LENGTH_SHORT).show();
//                    mWebView.loadUrl("https://m.taobao.com/");
                    //调用js中的方法
                    mWebView.loadUrl("javascript:callFromAndroid()");
                }
            });
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        //调用JS中的alert,采用Android中的Dialog进行替换
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

            Builder builder = new Builder(MainActivity.this);
            builder.setTitle("温馨提示:")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });
//                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            result.cancel();
//                        }
//                    });

            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });

            // 禁止响应按back键的事件
             builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
//             return super.onJsConfirm(view, url, message, result);
        }
    }
}
