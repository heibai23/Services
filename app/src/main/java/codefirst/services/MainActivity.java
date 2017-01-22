package codefirst.services;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView message_tv;
    private Button change_btn;
    private Button skipService;
    private ProgressBar asyTask_pb;

    public static final int SEND_MESSAGE=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {    //改变UI的操作在主线程
            super.handleMessage(msg);
            switch (msg.what){
                case SEND_MESSAGE:
                    message_tv.setText("通过线程改变了UI");
                  break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message_tv= (TextView) findViewById(R.id.tv_message);
        change_btn= (Button) findViewById(R.id.btn_changeTV);
        skipService= (Button) findViewById(R.id.btn_skipService);
        asyTask_pb= (ProgressBar) findViewById(R.id.pb_asyncTask);
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //耗时操作在线程中完成
                new Thread(new Runnable() {         //线程构造函数接收Runnable对象
                    @Override
                    public void run() {         //具体逻辑
                        Message message=new Message();
                        message.what=SEND_MESSAGE;
                        handler.sendMessage(message);       //将Message发出
                    }
                }).start(); //执行
            }
        });
        skipService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    //异步处理机制   启动：new DownloadTask().execute();
    class DownloadTask extends AsyncTask<Void,Integer,Boolean>{

        /***执行具体的耗时任务*/
        @Override
        protected Boolean doInBackground(Void... voids) {   //处理所有的耗时任务，通过return返回结果。不可进行UI操作

            return null;
        }
        /****/
        @Override
        protected void onPreExecute() {         //在后台任务开始执行前调用，进行一些界面上的初始化操作
            super.onPreExecute();
        }

        /***进行UI操作
         * 调用了publishProgress(Progress)后，此方法很快被调用。携带的参数由后台任务中传递出来。
         * //可对UI操作，利用参数中的数值可对界面元素更新
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /***
         *执行一些收尾工作
         * @param aBoolean
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {        //doInBackground()后很快...返回的数据作为参数传递到此方法中，可利用此进行一些UI操作
            super.onPostExecute(aBoolean);
        }
    }
}
