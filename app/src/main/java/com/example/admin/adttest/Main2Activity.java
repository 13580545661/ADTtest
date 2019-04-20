package com.example.admin.adttest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.net.URLConnection;


public class Main2Activity extends Activity {
    private Button button1;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String URL = "http://img.mp.itc.cn/upload/20170221/579c7d2769fd4ee2b6d4c460cd1c4b9c_th.jpg";
    private MyTask mTask = new MyTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 初始化
                init();
                // 执行线程
                mTask.execute(URL);
            }
        });



    }

    private void init() {

        mImageView = findViewById(R.id.image);
        mProgressBar = findViewById(R.id.progress_bar);
    }


    private class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
            mProgressBar.setVisibility(View.GONE);

        }




        @Override
        protected Bitmap doInBackground(String... params) {
            // 从params可变长数组中获取传递进来的url参数
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream in = null;
            BufferedInputStream buffer = null;

            try {
                connection = new URL(url).openConnection();
                in = connection.getInputStream();
                buffer = new BufferedInputStream(in);
                // 输入流转化为bitmap对象，利用decodeStream方法来解析输入流
                Thread.sleep(2000);
                bitmap = BitmapFactory.decodeStream(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 返回bitmap
            return bitmap;
        }
    }
}

