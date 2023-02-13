package com.example.app0130;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class LinearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //스윙을 포함한 모든 GUI 어플리케이션은 화면에 UI 컴포넌트를 올려놓기 전에 반드시 배치방법을 결정해야 한다
        //안드로이드에서 지원하는 배치방법은 여러방법이 있으며 다음과 같다
        /*
        LinearLayout : 선셩 레이아웃 (수직, 수평의 방향성이 있는 레이아웃, )
                            FlowLayout과 흡사
                            레이아웃 + 컨테이너를 합침

        GridLayout
        RelativeLayout
        Tablelayout
        ConstraintLayout
         */

        //리니어 생성하기
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //버튼 생성
        for(int i=1; i<50; i++) {
            Button bt = new Button(this);
            bt.setText("버튼"+i); //크기 설정하지 않으면 전체 크기가 됨

            layout.addView(bt);//레이아웃에 버튼 추가
        }
        //화면에 반영
        setContentView(layout); //(이 안엔)   LinearLayout ^layout^ = new LinearLayout(this);
    }
}