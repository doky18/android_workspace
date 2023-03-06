package com.example.photoapp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadManager {
    //Http 통신을 위한 객체
    HttpURLConnection con;
    String host="http://172.30.1.97:7777/admin/rest/product";
    String hypen="--";
    String boundary="***********";			//하이픈으로 감쌀 데이터의 경계 기준 문자열
    String line="\r\n";
    File file;		//유저가 전송을 위해 선택한 파일

    public void regist(Product product, File file) throws MalformedURLException, IOException {

        URL url = new URL(host);
        con = (HttpURLConnection) url.openConnection();

        //웹전송을 위한 머리와 몸을 구성하자!
        //머리 구성하기
        con.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
        con.setRequestMethod("POST");
        con.setDoOutput(true);        //서버에 보낼때
        con.setDoInput(true);            //서버에서 가져올때
        con.setUseCaches(false);    //
        con.setConnectTimeout(2500);

        //몸체 구성하기 (스트림으로 처리)
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());        //바디를 구성할 스트림

        //텍스트파라미터의 시작을 알리는 구분자 선언
        ds.writeBytes(hypen + boundary + line);                //시작할 때 hypen + ~~ + line
        //바디를 구성하는 요소들간에는 줄바꿈으로 구분한다
        ds.writeBytes("Content-Disposition:form-data;name=\"category_idx\"" + line);        //파라이터 선언 뒤에는 줄바꿈 표시가 필수이다
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8" + line);
        ds.writeBytes(line);            //값 지정 직후에는 라인으로 또 구분한다
        ds.writeBytes(product.getCategory_idx() + line);        //여기에도 라인이 들어가야함


        ds.writeBytes(hypen + boundary + line);                //시작 할 때 hypen + ~~ + line
        //바디를 구성하는 요소들간에는 줄바꿈으로 구분한다
        ds.writeBytes("Content-Disposition:form-data;name=\"t_brand\"" + line);        //파라이터 선언 뒤에는 줄바꿈 표시가 필수이다
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8" + line);
        ds.writeBytes(line);            //값 지정 직후에는 라인으로 또 구분한다
        ds.writeBytes(product.getProduct_name() + line);        //여기에도 라인이 들어가야함

        ds.writeBytes(hypen + boundary + line);                //시작할 때 hypen + ~~ + line
        //바디를 구성하는 요소들간에는 줄바꿈으로 구분한다
        ds.writeBytes("Content-Disposition:form-data;name=\"t_price\"" + line);        //파라이터 선언 뒤에는 줄바꿈 표시가 필수이다
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8" + line);
        ds.writeBytes(line);            //값 지정 직후에는 라인으로 또 구분한다
        ds.writeBytes(product.getPrice() + line);        //여기에도 라인이 들어가야함

        ds.writeBytes(hypen + boundary + line);                //시작할 때 hypen + ~~ + line
        //바디를 구성하는 요소들간에는 줄바꿈으로 구분한다
        ds.writeBytes("Content-Disposition:form-data;name=\"t_discount\"" + line);        //파라이터 선언 뒤에는 줄바꿈 표시가 필수이다
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8" + line);
        ds.writeBytes(line);            //값 지정 직후에는 라인으로 또 구분한다
        ds.writeBytes(product.getDiscount() + line);        //여기에도 라인이 들어가야함


        ds.writeBytes(hypen + boundary + line);                //시작할 때 hypen + ~~ + line
        //바디를 구성하는 요소들간에는 줄바꿈으로 구분한다
        ds.writeBytes("Content-Disposition:form-data;name=\"t_detail\"" + line);        //파라이터 선언 뒤에는 줄바꿈 표시가 필수이다
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8" + line);
        ds.writeBytes(line);            //값 지정 직후에는 라인으로 또 구분한다
        ds.writeBytes(product.getDetail() + line);        //여기에도 라인이 들어가야함

        //파일 파라미터 처리
        ds.writeBytes(hypen + boundary + line);                //시작할떄
        ds.writeBytes("Content-Disposition:form-data;name=\"photo\";filename=\"" + file.getName() + "\"" + line);
        ds.writeBytes("Content-Type:image/jpeg" + line);  //파일의 종류, 형식
        ds.writeBytes(line);

        //파일쪼개서 전송
        FileInputStream fis = new FileInputStream(file);
        byte[] buff = new byte[1024];

        int data = -1;
        while (true) {
            data = fis.read(buff);
            if (data == -1) break;
            ds.write(buff);
        }
    }
}
