package codefirst.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by cheng on 2017/1/21.
 */

/***异步+自动停止的服务
 * 实现线程+停止（运行结束自动停止）
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {  //无参构造方法
        super("MyIntentService");       //调用父类有参构造函数
    }
    //处理具体逻辑
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("tag","Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag","自动结束");
    }
}
