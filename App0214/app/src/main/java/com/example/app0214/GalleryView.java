package com.example.app0214;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

//뷰를 상속받아 그래픽처리를 개발자가 주도해보자
public class GalleryView extends View {
    Context context;        //이 뷰를 관리하는 액티비티
    Bitmap[] bitmaps= new Bitmap[4];
    int index=0;      //사진배열을 접근할 인덱스


    //alt + insert -> 생성자 만들기
   // public GalleryView(Context context) {
   //     super(context);
  //      this.context=context;
   //     init();
  //  }

    //위 보다 더 개선된 생성자 사용
    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    //비트맵 배열을 미리 만들어 놓기
    public void init(){
        bitmaps[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.grass1);
        bitmaps[1]=BitmapFactory.decodeResource(context.getResources(), R.drawable.horang);
        bitmaps[2]=BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);
        bitmaps[3]=BitmapFactory.decodeResource(context.getResources(), R.drawable.mom);
                //왼쪽 res 디렉토리에 있는 애들 가져올 때 (사진과 같은) -> (경로, 파일이름)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //이미지를 그리자
        canvas.drawBitmap(bitmaps[index],50, 50, null);
    }

    //다음 이미지
    public void nextImg(){      //이건 버튼들이 인식해서 호출해야함
       index++;
       invalidate();    //다시그리기 java repaint()와 동일
    }

    //이전 이미지
    public void prevImg(){
        index--;
        invalidate();
    }
}
