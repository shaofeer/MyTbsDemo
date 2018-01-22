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
