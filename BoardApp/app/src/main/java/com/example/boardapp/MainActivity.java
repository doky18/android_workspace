package com.example.boardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText t_title, t_writer, t_content;
    Button bt_regist, bt_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t_title = findViewById(R.id.t_title);
        t_writer = findViewById(R.id.t_writer);
        t_content = findViewById(R.id.t_content);
        bt_regist = findViewById(R.id.bt_regist);
        bt_list = findViewById(R.id.bt_list);

        bt_regist.setOnClickListener((v)->{
            Thread thread = new Thread(){
                    public void run(){
                request();      //웹서버에 요청하기

                }
            };
                thread.start();
        });
    }

    private void request() {
        // 요청용 출력 스트림 : 얘를 이용하여 파라미터를 전송 할 것임
        DataOutputStream dos = null;

        // 서버의 응답받기용
        BufferedReader buffr = null;
        InputStreamReader is = null;

        try {
            URL url = new URL("http://172.30.1.54:7777/rest/notice/regist");
            URLConnection uCon = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection) uCon;
            httpCon.setRequestMethod("POST");

            // 파라미터 만들기
            String postData = "title=Hi&writer=doky&content=cozy";
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            // POST 전송 application/x-www-form-uriencoded
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 출력스트림을 이용하여 데이터 전송할 예정
            // httpCon.getOutputStream();
            dos = new DataOutputStream(httpCon.getOutputStream());
            dos.writeBytes(postData); // 포스트 전송

            // 서버의 응답 정보 받기
            is = new InputStreamReader(httpCon.getInputStream());
            buffr = new BufferedReader(is);

            String msg = null;
            while (true) {
                buffr.readLine();
                if (msg == null)
                    break;

                System.out.println(msg);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffr != null) {
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}