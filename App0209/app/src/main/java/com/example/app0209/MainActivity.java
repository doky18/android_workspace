package com.example.app0209;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView() 메서드에 의해 xmll이 읽혀지고 해석되어지므로
        //xml에 명시된 뷰를 얻기 위해서는 setContentView()메서드 이후에 접근해야한다
        setContentView(R.layout.list_view);
        //R은 R.java 라는 상수를 모아놓은 클래스이다
        //R.java 프로젝트 구성 디렉토리 중 res 디렉토리를 반영한 클래스이다
        //즉 개발자가 res 디렉토리에 xml, 이미지나, 음원 등등 리소스를 등록하면
        //개발환경 자체에서 실시간으로 R.java에 상수로 등록...
        ListView listView=(ListView) this.findViewById(R.id.listView);    //document.getElementById()와 흡사

        //ListView에 데이터를 채우기 위해 ArrayList를 사용할 수 있다
        //ListView, GridView는 일명 어댑터뷰라 불리며 MVC로 분리되어 있기 때문에..
        //데이터는 MVC에서 M에 해당 (왜냐면 나중에 db에서 가져올 것이니까)
        ArrayList <String> list = new ArrayList<String>();
        list.add("초콜릿");
        list.add("딸기");
        list.add("메론");
        list.add("무화과");
        list.add("망고");
        list.add("우유");

        //어댑터 생성
        //어댑터의 생성자 매개변수 중 두번째 매개변수에는 아이템을 담게 될 뷰가 올 수 있다
        //특히 이 뷰는, xml 레이아웃 파일로 처리되어야 하는데
        //이 파일은 개발자가 직접 생성해도 되고, 이미 안드로이드 자체에서 지원하는 xml 파일을 이용할 수도 있는데
        //어디까지나 개발자의 선택임. 지노의 선택은 이미 있는 걸 쓰기임
        //참고로 현재 프로젝트의 res와 연계된 자바 클래스는 R.java(프로젝트 소속)이고,
        //안드로이드 시스템 자체에서 지원하는 리소스와 연계된 자바 클래스는 그냥 R.java가 아닌 android.R.java(시스템 소속)이다
        //후자가 더 범용성이 크다
                                                                                                                                              //simple=xml
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

        //어댑터 반영하기
        listView.setAdapter(adapter);
    }
}
