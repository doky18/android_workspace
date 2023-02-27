package com.example.fragmentbasic;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/* 액티비티가 화면 전체를 관리한다면,
프레그먼트는 액티비티의 화면 일부를 관리하는 목적으로 지원되는 객체이다*/
public class HomeFragment extends Fragment {
    private String TAG = this.getClass().getName();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()호출 : 액티비티와 연결될 때 호출");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()호출 : 프레그먼트가 초기화 될 때 호출");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프레그먼트가 사용할 xml 레이아웃 파일 인플레이션 시키기.
        Log.d(TAG, "onCreateView()호출 : 프레그먼트가 초기화 된 후 뷰를 구성할 때 호출");

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        // false : 인플레이션 후 생성된 View를 어디에도 부착하지 않는다
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()호출 : 프레그먼트와 연결된 액티비티가 onStart()되어 완전히 보여지기 직전에 호출");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()호출 : 프레그먼트와 연결된 액티비티가 onStart()되어 뷰를 완전히 보여질 때 호출, 이때부터 사용자는 UI를 제어(터치)할 수 있다");
    }
    //터치 이벤트를 주려면 onResume() 이후에 해야 함


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()호출 : 프레그먼트와 연결된 액티비티가 onPause()되었을 때, 사용자는 UI를 제어(터치)할 수 없다");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()호출 : 프레그먼트와 연결된 액티비티가 onStop()되어 화면에서 완전히 가려졌을때, 프레그먼트는 중지가 된다");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()호출 : 프레그먼트와 관련된 뷰 리소스 해제 시 호출됨");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()호출 : 프레그먼트 종료 직전 호출, 이때 개발자는 프레그먼트와 관련된 상태를 마지막으로 정리 할 수 있음");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()호출 : 프레그먼트가 액티비티와 연결을 끊기 직전 호출 됨");
    }
}