package codefirst.services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cheng on 2017/1/21.
 */

public class ServiceActivity extends AppCompatActivity {

    private Button start_btn;
    private Button pause_btn;
    private Button bind_btn;
    private Button unbind_btn;
    private Button intentService_btn;
    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);

        init();


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MyService.class);  //跳转Service
                startService(intent);   //启动服务
            }
        });
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MyService.class);  //跳转Service
                stopService(intent);   //停止服务
            }
        });
        //绑定点击事件
        bind_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bindIntent=new Intent(getApplicationContext(),MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                                            //标志位，当绑定后自动创建服务
            }
        });
        unbind_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent unbindIntent=new Intent(getApplicationContext(),MyService.class);
                unbindService(connection);
            }
        });
        intentService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService=new Intent(getApplicationContext(),MyIntentService.class);
                startService(intentService);
            }
        });
    }

    private void init() {
        start_btn= (Button) findViewById(R.id.btn_start);
        pause_btn= (Button) findViewById(R.id.btn_pause);
        bind_btn= (Button) findViewById(R.id.btn_binService);
        unbind_btn= (Button) findViewById(R.id.btn_unbinService);
        intentService_btn= (Button) findViewById(R.id.btn_intentService);
    }

    /***类似于中介，将绑定时要做的服务保存起来，当绑定时则便是在控制这里的服务
     * 重写绑定和解绑的方法
     */
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder= (MyService.DownloadBinder) iBinder; //强制转换
            downloadBinder.startDownload(); //调用服务的方法
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //downloadBinder.getProgress();  不会执行，直接调用onDestroy()

        }
    };
}
