## WebView的使用

### JS与Android中方法的互调
* JS调用Android中的方法

#### 调用JS中的方法
* 调用形式:`mWebView.loadUrl("javascript:callJavaScriptMethod()");`
* 说明:wave为JS中的方法

#### 调用JS中的弹窗(alert,confirm和prompt)
* 调用方式: 调用setWebChromeClient方法,复写`onJsAlert`,`onJsConfirm`和`onJsPrompt`方法
    
* Android调用JS中的方法
    * 调用形式:`<a onClick="window.obj.invokeAndroid()">`
    * 说明:
        1. `obj`为android中指定的调用名称;
        2.  添加`mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "obj");`
        3. MyJavaScriptInterface为一个类,该类中有invokeAndroid()的方法,且该方法必须为public.
        
### 支持https页面的访问
* 如果出现某些https的网站无法访问,首先需要添加支持JS

    ```
    WebSettings webSettings = mWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    ```
* 在2.2及以上系统中处理只需要重载WebViewClient 的 onReceivedSslError即可。

    ```
    webview.setWebViewClient(new WebViewClient() {  
        @Override  
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {    
            //handler.cancel(); 默认的处理方式，WebView变成空白页   
            //handler.process();接受证书    
            //handleMessage(Message msg); 其他处理  
        }
    });
    ```