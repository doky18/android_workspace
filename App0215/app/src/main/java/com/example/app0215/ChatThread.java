package com.example.app0215;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

//소켓을 보관하고 + 각각 독립적으로 해당 소켓을 이용하여 
//메시지를 주고받는 객체가 되어야 함 == 스레드
public class ChatThread implements Runnable{
	//Runnable은 이미 해당 클래스가 누군가의 자식일 경우 스레드를 상속할 수 없을 때 
	//사용할 수 있는 인터페이스이다.
	//주의할 점은 Runnable은 스레드 자체가 아닌, 그냥 run()메서드만을 보유한 객체이다
	//따라서 Runnable을 구현하더라도 thread 객체는 필요하다
	
	Thread thread;
	Socket socket;		//서버소켓이 접속자를 발견하면, 그때 넘겨받게 되는 소켓 
	BufferedReader buffr;		//버퍼처리된 문자기반의 입력스트림
	BufferedWriter buffw;		//버퍼처리된 문자기반의 출력스트림
	Context context;
	MainActivity mainActivity;
	
	Handler handler;		//스레드가 디자인을 접근하지 못하므로, 대신 메인에게 제어할 것을 부탁해 주는 객체
	
	public ChatThread(Socket socket, Context context) {	//생성자
		thread = new Thread(this);			//러너블 구현 객체를 매개변수로 넣는다
												//이때부터 Runnable의 run메서드와 스레드 객체가 연계된다
		this.socket=socket;
		this.context=context;
		mainActivity=(MainActivity) context;

		handler= new handler(Looper.myLooper()){
			public void handlerMessage(""){

			}
		}

		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		thread.start();		//태어날 때 부터 청취 시작
	}

	//듣기
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine();
			mainActivity.t_view.append(msg+"\n");		//누적

			//핸들러에게 부탁할 예정
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("msg", msg);
			message.setData(bundle);

			handler.handleMessage(null);		//핸들러에 전달...

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		//말하기
		public void send(String msg) {
			try {
				buffw.write(msg+"\n");
				buffw.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	//무한청취 시작
	public void run() {
		while(true) {
			listen();		
			
		}
	}

}
