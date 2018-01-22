package top.wintp.mytbsdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

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
