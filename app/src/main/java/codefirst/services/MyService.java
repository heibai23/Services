package codefirst.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cheng on 2017/1/21.
 */
/***服务类  每个服务均需要注册  活动与服务进行通信，需要将二者进行绑定（一个服务可和任何一个其他活动绑定）
 * *******onCreate()在服务创建时调用，一次
 * ******onStartCommand()每次启动时调用
 * *****onDestroy()回收资源
 */

public class MyService extends Service {


    private DownloadBinder mBinder=new DownloadBinder();    //匿名对象
    @Nullable
    @Override       //用来实现在Activity中控制Service，将要做的服务封装起来，等待绑定然后执行
    public IBinder onBind(Intent intent) {
        return mBinder; //返回的是匿名对象

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag","onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("tag","onStartCommand()"+"不在线程中");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag","onDestroy()");
    }
    //绑定Activity和Service，用于控制Service
    class DownloadBinder extends Binder{
        //开始下载--（模拟）
        public void startDownload(){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Log.d("tag","开始下载："+"在线程中");
                    stopSelf(); //服务停止  作用？
                }
            }).start(); //开启线程
        }
        public int getProgress(){
            Log.d("tag","获取进度");
            return 0;
        }
    }
}
