package com.example.noticeclient;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
Swing 에서의 TableModel과 거의 동일한 목적의 클래스 정의
 */
public class NoticeAdapter extends BaseAdapter {
    private String TAG = this.getClass().getName();     //로그 검색어로 사용할 것임
    //안드로이드 시스템에 너무나 많은 로그가 찍히므로, 필터를 적용하려 걸러내려면
    //필터 키워드가 있어야 하는데, 현재 클래스명을 해당 키워드로 쓰기위해...

    ListActivity listActivity;

    List<Notice> noticeList = new ArrayList<Notice>();
    LayoutInflater layoutInflater;      //xml을 읽어들여 android view api들을 생성해줌
                                                //이 객체는 액티비티를 통해 얻을 수 있다

     public NoticeAdapter(ListActivity listActivity){
        this.listActivity = listActivity;
        layoutInflater = (LayoutInflater) listActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        test();
     }

    //db연동 없이 테스트용으로
    public void test(){
        for(int i=0;i<50;i++){
            Notice notice = new Notice();
            notice.setNotice_idx(i);
            notice.setTitle(i+"번째 제목입니다");
            notice.setWriter(i+"번째 작성자입니다");
            notice.setRegdate("2023-02-"+i);

            noticeList.add(notice);
        }
    }
    //아이템의 수
    public int getCount() {
        return noticeList.size();
    }

    //지정된 인덱스의  데이터 하나 (Notice)반환
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    //아이템에 부여할 고유값 ( idx 를 활용 )
    public long getItemId(int i) {
        Notice notice=noticeList.get(i);
        return notice.getNotice_idx();
    }
    //해당 아이템이 화면에 등장할 때 호출되는 메서드이며, 이 메서드를 어떻게 사용하느냐에 따라
    //앱의 성능이 좌우됨
    public View getView(int i, View view, ViewGroup viewGroup) {
        /*
        1) getView() 메서드는 몇번 호출하는가? 레코드 수만큼 즉 getCount() 반환값 만큼
        2) 여기서 return 해야 할 view는 무엇인가?
            ListView의 한 아이템을 표현하는 뷰가 와야하고, 우리는 item_notice.xml 파일로 디자인을 해놓았다
            하지만 Java가 아니므로, 그 xml을 읽어들여 객체화 시켜야 한다 (인플레이션션
         */

        Log.d(TAG, "i="+i+", view="+view+", viewGroup="+viewGroup);
        //이미 태어난 뷰가 넘어온 기존의 넝어온 view를 그대로 이요아자

        //레코드 수 만큼 인플레이션 시키기
        if(view==null) {
            view = layoutInflater.inflate(R.layout.item_notice, viewGroup, false);
        }

        Button bt_view = view .findViewById(R.id.bt_view);
        bt_view.setText(i + "");       //재활용 여부를 알기위해 표시하려고

        TextView t_title=view.findViewById(R.id.t_title);
        TextView t_writer=view.findViewById(R.id.t_writer);
        TextView t_regdate=view.findViewById(R.id.t_regdate);
        Notice notice = noticeList.get(i);
        bt_view.setOnClickListener((v)->{
            Log.d(TAG,"선택한 글의 idx : "+notice.getNotice_idx());
            //상세 페이지를 담당하는 액티비티 호출 (new 불가, 서블릿과 동일)
            Intent intent = new Intent(listActivity, DetailActivity.class);       //여기서(내부익명 안인걸 기억해), 어디로?
            //웹이 아니므로, 파라미터 전송 시 intent에 담고 가야함
            //intent : 안드로이드의 컴포넌트 간 데이터 전달 객체
            // 안드로이드의 주요 컴포넌트 (개발자가 아닌 시스텡넹 의해 객체가 관리됨):
            //1. Activity
            //2. Content Provider
            //3. Service
            //4. Broadcast Receiver
            intent.putExtra("notice_idx", notice.getNotice_idx());
            listActivity.startActivity(intent);

            //이제 detail 만나러 갑니다.........

        });

        t_title.setText(notice.getTitle());
        t_writer.setText(notice.getWriter());
        t_regdate.setText(notice.getRegdate());

        return view;        //여기서 리턴 되는 view는 처음에 받은 매개변수 View view(기존에 살았던 애는 살려서 보내줌줌)
    }
}
