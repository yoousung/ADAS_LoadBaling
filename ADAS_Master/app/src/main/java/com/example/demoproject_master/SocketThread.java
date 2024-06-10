package com.example.demoproject_master;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

// Device 1개에 이미지 데이터 전송
public class SocketThread implements Runnable{

    private Bitmap bmp;

    //TODO 원하는 IP로 수정
    private String ip_data;

    public SocketThread(Bitmap bmp, String ip_data) {
        this.bmp = bmp;
        this.ip_data = ip_data;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(ip_data, 1357);

            // 서버로 이미지 전송
            BufferedOutputStream outToServer = new BufferedOutputStream(clientSocket.getOutputStream());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            // byte array and recycle call for better performance
            byte[] byteArray = stream.toByteArray();
            bmp.recycle();

            outToServer.write(byteArray);
            outToServer.flush(); // 버퍼 비우기
            clientSocket.close();

        }catch(Exception e){
            Log.e(StateSingleton.getInstance().TAG, "SocketThread runs on an error!");
        }
    }
}