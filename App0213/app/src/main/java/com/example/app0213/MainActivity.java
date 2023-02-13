package com.example.app0213;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();     //현재 클래스를 키워드로 검색해서 잡아 낼 수 있게
    EditText t_input;
    List nationList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xml을 반드시 써야하는 것은 아니지만, 스윙처럼 직접 자바코드만으로
        setContentView(R.layout.activity_main);

        //xml로부터 뷰 객체들이 태어나는 과정을 inflation이라 한다
        //setContentView 메서드 호출 이후부터는 id만 알면 인스턴스를 접근할 수 있다
        //이때 아이디를 통해 접근하는 메서드가 findViewById (getElementById와 흡사)
        Button bt_regist =(Button) findViewById(R.id.bt_regist);
        t_input=(EditText)findViewById(R.id.t_input);

        //xml 문서에 있는 ListView를 제어하기 위해 id 를 이용하여 레퍼런스 얻기
        ListView listView = (ListView) this.findViewById(R.id.listView);

        //List, GridView 일명 어댑터를 이용한다 하여 어댑터뷰라하는데
        //주로 목록을 처리하는데 압도적으로 많이 씀씀
        //swing에서의 JTable 이 TableModell을 이용하여 데이터를 연동하는 것과 동일
        //JTable - ListView, GridView 등의 어댑터 뷰
        // TableModel - Adapter
        nationList = new ArrayList();
        nationList.add("미국");
        nationList.add("일본");
        nationList.add("영국");
        nationList.add("튀르키에");
        nationList.add("스위스");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nationList);
        listView.setAdapter(adapter);


        //버튼과 리스너 연결
        bt_regist.setOnClickListener((v)->{
            Log.d(TAG, "눌렀어?");
            regist();
        });
    }

    public void regist(){
        //입력창에 입력한 값을 List로 얻어옴
        String value = t_input.getText().toString();
        nationList.add(value);
        //어댑터 새로고침
        adapter.notifyDataSetChanged();;
    }

}
