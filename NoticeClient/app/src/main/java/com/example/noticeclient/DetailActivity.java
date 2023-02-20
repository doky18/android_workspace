package com.example.noticeclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DetailActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    EditText t_title, t_writer, t_content;
    int notice_idx;
    Handler handler;
    Handler handler2;
    Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        t_title = findViewById(R.id.t_title);
        t_writer = findViewById(R.id.t_writer);
        t_content = findViewById(R.id.t_content);

        Button bt_edit = findViewById(R.id.bt_edit);
        Button bt_del = findViewById(R.id.bt_del);
        Button bt_list = findViewById(R.id.bt_list);

        //나를 호출한 액티비티가 혹여나, Intent안에 뭔가를 넣었다면 꺼내먹자!
        Intent intent = this.getIntent();   //새로운 인텐트가 아니라 다른 액티비티에서 전달한 인텐트이다
        notice_idx = intent.getIntExtra("notice_idx", 0);

        Log.d(TAG, "이 액티비티가 초기화 될 떄 넘겨받은 notice_idx" + notice_idx);


        //수정하기
        bt_edit.setOnClickListener((v) -> {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    edit();
                }
            };
            thread.start();
        });

        //삭제하기
        bt_del.setOnClickListener((v) -> {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    del();
                }
            };
            thread.start();
        });

        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                //이 메서드 영역은 main쓰레드에 의해 구현된다!
                //즉 디자인 제어가 가능...
                t_title.setText(notice.getTitle());
                t_writer.setText(notice.getWriter());
                t_content.setText(notice.getContent());
            }
        };

        handler2 = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                goList();
            }
        };

        //목록보기
        bt_list.setOnClickListener((v) -> {
            goList();
        });

    }

    public void goList() {
        this.finish();          //창 닫기
    }

    public void del() {
        //get방식으로 요청
        BufferedReader buffr = null;
        InputStreamReader reader = null;

        try {
            URL url = new URL("http://172.30.1.54:7777/rest/notice/del?notice_idx=" + notice_idx);
            URLConnection uCon = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection) uCon;
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);

            int code = httpCon.getResponseCode(); //200, 404, 500...
            Log.d(TAG, "삭제 서버의 응답정보" + code);

            if (code == HttpURLConnection.HTTP_OK) {
                reader = new InputStreamReader(httpCon.getInputStream(), "UTF-8");
                buffr = new BufferedReader(reader);

                StringBuilder sb = new StringBuilder();
                String msg = null;
                while (true) {
                    msg = buffr.readLine();
                    if (msg == null) break;
                    sb.append(msg);
                }
                Log.d(TAG, sb.toString());

                //파싱~~~게시물이 1건 이므로 단수형 JSON이다
                //JSONObject json = new JSONObject(sb.toString());
                //convertJsonToObject(json);

                handler2.sendEmptyMessage(0);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (buffr != null) {
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void edit() {
        //Http Request
        BufferedWriter buffw = null;
        OutputStreamWriter os = null;
        BufferedReader buffr = null;

        InputStreamReader is = null;
        DataOutputStream dos = null;
        try {
            //외부에서 접속하는 것이기 때문에 풀경로(이클립스 RestNoticeController.java)를 명시해야 함
            URL url = new URL("http://172.30.1.54:7777/rest/notice/edit?");  //ip주소:포트번호/자바 파일 주소
            URLConnection uCon = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection) uCon;
            httpCon.setRequestMethod("POST");
            //내가 보내고자하는 데이터가 텍스트 형식이다
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setDoOutput(true);

            //보낼 데이터
            String title = t_title.getText().toString();
            String writer = t_writer.getText().toString();
            String content = t_content.getText().toString();

            //받을 파라미터들
            String postData="title="+title+"&writer="+writer+"&content="+content+"&notice_idx="+notice_idx;
            os = new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8");
            buffw = new BufferedWriter(os);
            dos = new DataOutputStream(httpCon.getOutputStream());
            dos.writeBytes(postData);

            //데이터 요청 이후에 입력스트림을 만들어야 한다
            is = new InputStreamReader(httpCon.getInputStream(), "UTF-8");
            buffr = new BufferedReader(is);

            StringBuilder sb = new StringBuilder();
            while (true) {
                String result = buffr.readLine();
                sb.append(result);
                if (result == null) {
                    break;
                }
            }
            Log.d(TAG, sb.toString()); //스프링 컨트롤러에 적어둔 return 메시지가 나와야함


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (buffr != null) {
                try {
                    buffr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (buffw != null) {
                try {
                    buffw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void convertJsonToObject(JSONObject json) {
        //멤버변수의 DTO에 값을 채워놓고, 핸들러에게 접근하도록하자

        notice = new Notice();
        try {
            notice.setNotice_idx(json.getInt("notice_idx"));
            notice.setTitle(json.getString("title"));
            notice.setWriter(json.getString("writer"));
            notice.setContent(json.getString("content"));
            notice.setRegdate(json.getString("regdate"));
            notice.setHit(json.getInt("hit"));

            //화면에 반영하기 (네트워크 통신은 개발자 정의 스레드에 의해 진행되었으므로 화면에 반영하는 권한은
            //main스레드에 있기 때문에 핸들러에 의해 간접 제어)
            handler.sendEmptyMessage(0);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public void getDetail() {
        //Get방식의 요청시도! json으로 가져오기
        BufferedReader buffr = null;
        InputStreamReader reader = null;

        try {
            URL url = new URL("http://172.30.1.54:7777/rest/notice/detail?notice_idx=" + notice_idx);
            URLConnection uCon = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection) uCon;
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);

            int code = httpCon.getResponseCode(); //200, 404, 500...
            Log.d(TAG, "상세보기 서버의 응답정보" + code);

            if (code == HttpURLConnection.HTTP_OK) {
                reader = new InputStreamReader(httpCon.getInputStream(), "UTF-8");
                buffr = new BufferedReader(reader);

                StringBuilder sb = new StringBuilder();
                String msg = null;
                while (true) {
                    msg = buffr.readLine();
                    if (msg == null) break;
                    sb.append(msg);
                }
                Log.d(TAG, sb.toString());

                //파싱~~~게시물이 1건 이므로 단수형 JSON이다
                //단수는 Object, 복수형은 Array
                JSONObject json = new JSONObject(sb.toString());
                // []  --> ArrayList   {} --> Notice
                convertJsonToObject(json);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (buffr != null) {
                try {
                    buffr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread thread = new Thread() {
            public void run() {
                getDetail();
            }
        };
        thread.start();
    }
}