##Tencent TBS （下简称TBS）
####腾讯浏览服务

###What is it?
>百度百科解释：
>
>腾讯浏览服务（Tencent Browsing Service，以下简称TBS），由腾讯X5浏览服务升级而来，作为业界首个升级至X5 blink内核，与Android 5.0 版本的Chromium M37一致，解决了Android 多平台兼容性问题。

>在我们的手机上如何找到它？微信打开一片公众号的文章，下拉会出现（QQ浏览器X5内核提供技术支持）。QQ/TIM内置浏览器。QQ浏览器，都使用了x5内核。

TIM？如果你还没有用过Tim，可以点击下面的连接进行下载(个人感觉比QQ好用)：
>[https://tim.qq.com/htdocs/vip/share.html?_wv=16777216&_wvSb=1&uid=*St*MzM3MDgxMjY3](https://tim.qq.com/htdocs/vip/share.html?_wv=16777216&_wvSb=1&uid=*St*MzM3MDgxMjY3 "Tim使用")


###Why use it?

- 让你的app实现基本网页加载（类浏览器功能）
- 让你的app实现播放视频功能
- 让你的app实现播放直播流功能

###How use it?

- 第一步：访问TBS官网，下载SDK for Android
- 第二步：根据集成文档，集成到自己的项目中
- 第三步：编写自己的代码进行测试。


###下载TBS SDK
官网：[https://x5.tencent.com/](https://x5.tencent.com/ "TBS官网")

点击首页的SDK下载，

点击下载完整版，或者点击分享链接：
>链接：[https://pan.baidu.com/s/1jJujWiE](https://pan.baidu.com/s/1jJujWiE "百度分享") 密码：sxkg
点击下载SDK接入示例-Android Studio，或者点击分享链接：
>链接：链接：[https://pan.baidu.com/s/1kW0T44v](https://pan.baidu.com/s/1kW0T44v "百度分享") 密码：ca11


###SDK集成
1. 解压SDK压缩包，复制里面的jar包到自己工程里面的libs(tbs_sdk_thirdapp_v3.5.0.1004_43500_sharewithdownload_withoutGame_obfs_20170801_113025)文件夹中
2. 添加jar包到构建路径。如下图
图片！Q!
3. 查看官方接入文档：[https://x5.tencent.com/tbs/guide/sdkInit.html](https://x5.tencent.com/tbs/guide/sdkInit.html "接入文档") 
4. 添加so文件支持，解压SDK接入示例-Android Studio压缩包，复制jniLibs文件夹，粘贴到自己工程main包下。具体的操作查看：[https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/34cf1488-7dc2-41ca-a77f-0014112bcab7](https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/34cf1488-7dc2-41ca-a77f-0014112bcab7 "添加so文件")
5. 添加权限：在清单文件中添加如下权限
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

    <uses-permission android:name="android.permission.INTERNET"/>

    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
6. 在项目的Application中初始化x5内核
	

    /**
     *初始化X5
     */
    private void initX5() {
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        });
    }

7. 如果想实现视频播放功能可以在清单文件中配置该Activity

  	 <activity
            android:name="com.tencent.smtt.sdk.VideoActivity"
            android:alwaysRetainTaskState="true"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:exported="false"
            android:launchMode="singleTask">

            <intent-filter>
                <action android:name="com.tencent.smtt.tbs.video.PLAY"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </activity>



	
### 编写代码进行测试。	

MainActivity.java

	
	public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOpenUrl;
    private Button btnOpenMp4;
    private Button btnOpenM3u8;
    private Button btnOpenRtmp;
    private FrameLayout mFl_web_view_layout;
    private WebView mWebView;

    /**
     * 打开的Url
     */
    private String openUrl = "http://www.wintp.top";
    /**
     * m3u8视频的地址
     */
    private String m3u8Url = "http://www.flashls.org/playlists/test_001/stream_1000k_48k_640x360.m3u8";
    /**
     * m3u8视频的地址
     */
    private String mp4Url = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
    /**
     * rtmp视频的地址 香港卫视：rtmp://live.hkstv.hk.lxdns.com/live/hks
     */
    private String rtmp8Url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";


    @Override
    public void onClick(View v) {
        if (v == btnOpenUrl) {
            //加载URL
            initWebView();
        } else if (v == btnOpenMp4) {
            startVideo(mp4Url);

        } else if (v == btnOpenM3u8) {
            startVideo(m3u8Url);

        } else if (v == btnOpenRtmp) {
            startVideo(rtmp8Url);

        }
    }

    /**
     * 播放视频
     *
     * @param url
     */
    public void startVideo(String url) {
        //判断当前Tbs播放器是否已经可以使用。
        //public static boolean canUseTbsPlayer(Context context)
        //直接调用播放接口，传入视频流的url
        //public static void openVideo(Context context, String videoUrl)
        //extraData对象是根据定制需要传入约定的信息，没有需要可以传如null
        //public static void openVideo(Context context, String videoUrl, Bundle extraData)

        if ((TbsVideo.canUseTbsPlayer(this))) {
            //可以播放视频
            TbsVideo.openVideo(this, url);

        } else {
            Toast.makeText(this, "视频播放器没有准备好", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //x5 设置键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        btnOpenUrl = findViewById(R.id.btn_open_url);
        btnOpenMp4 = findViewById(R.id.btn_open_mp4);
        btnOpenM3u8 = findViewById(R.id.btn_open_m3u8);
        btnOpenRtmp = findViewById(R.id.btn_open_rtmp);
        mFl_web_view_layout = findViewById(R.id.fl_web_view_layout);

        btnOpenUrl.setOnClickListener(this);
        btnOpenMp4.setOnClickListener(this);
        btnOpenM3u8.setOnClickListener(this);
        btnOpenRtmp.setOnClickListener(this);

    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        //采用new WebView的方式进行动态的添加WebView
        //WebView 的包一定要注意不要导入错了
        //com.tencent.smtt.sdk.WebView;

        mWebView = new WebView(this);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

        mWebView.setLayoutParams(layoutParams);


        mWebView.loadUrl(openUrl);
        WebSettings settings = mWebView.getSettings();


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {

                webView.loadUrl(url);

                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {

                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {//处理https请
                //handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }


        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";

                } else {
                    // to do something...
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
            }


        });

        settings.setJavaScriptEnabled(true);

        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小


        //设置加载图片
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setNeedInitialFocus(false);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);//适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);//自动加载图片
        settings.setCacheMode(WebSettings.LOAD_DEFAULT
                | WebSettings.LOAD_CACHE_ELSE_NETWORK);


        //将WebView添加到底部布局
        mFl_web_view_layout.removeAllViews();
        mFl_web_view_layout.addView(mWebView);
    }


    @Override
    public void onBackPressed() {
	//监听返回键，判断webview是否能够后退，如果能后退，则执行后退功能如不能后退，则关闭该页面
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {

        //在Activity销毁的时候同时销毁WebView
        //如没有此操作，可能会出现，当你在网页上播放一个视频的时候，直接按home键退出应用，视频仍在播放
        if (mWebView != null) {
            mWebView.destroy();
            mFl_web_view_layout.removeView(mWebView);
            mWebView = null;
        }

        super.onDestroy();
    }
	}

activity_main.xml

		<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical">
	
	    <Button
	        android:id="@+id/btn_open_url"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="打开  wintp.top"/>
	
	    <Button
	        android:id="@+id/btn_open_mp4"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="打开  mp4"/>
	
	    <Button
	        android:id="@+id/btn_open_m3u8"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="打开直播流(m3u8)"/>
	
	    <Button
	        android:id="@+id/btn_open_rtmp"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="打开直播流(rtmp)"/>
	
	
	    <FrameLayout
	        android:id="@+id/fl_web_view_layout"
	        android:layout_width="match_parent"
	        android:layout_height="0dp"
	        android:layout_weight="1"/>
	</LinearLayout>


MyApp.java

		package top.wintp.mytbsdemo;
	
	import android.app.Activity;
	import android.app.Application;
	import android.os.Bundle;
	
	import com.tencent.smtt.sdk.QbSdk;
	
	/**
	 * 作者：  pyfysf
	 * <p>
	 * qq:  337081267
	 * <p>
	 * CSDN:    http://blog.csdn.net/pyfysf
	 * <p>
	 * 个人博客：    http://wintp.top
	 * <p>
	 * 时间： 2018/01/2018/1/22 15:42
	 * <p>
	 * 邮箱：  pyfysf@163.com
	 */
	public class MyApp extends Application {
	    @Override
	    public void onCreate() {
	        super.onCreate();
	
	
	        initX5();
	
	    }
	
	    /**
	     * 初始化X5
	     */
	    private void initX5() {
	        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
	            @Override
	            public void onCoreInitFinished() {
	
	            }
	
	            @Override
	            public void onViewInitFinished(boolean b) {
	
	            }
	        });
	    }
	}






该项目的github地址：
> [https://github.com/pyfysf/MyTbsDemo.git](https://github.com/pyfysf/MyTbsDemo.git "github")







### 注意：
视频功能：TBS 支持mp4，rmvb，flv，avi等26种视频格式。
本文只举：mp4,m3u8,rtmp三个格式。


