package com.example.demoproject_master;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

// Device 2개에 이미지 데이터 전송
public class SocketThread2 implements Runnable{

    private Bitmap bmp;

    //TODO 원하는 IP로 수정
    private String ip_data1;
    private String ip_data2;

    public SocketThread2(Bitmap bmp, String ip_data1, String ip_data2) {
        this.bmp = bmp;
        this.ip_data1 = ip_data1;
        this.ip_data2 = ip_data2;
    }

    @Override
    public void run() {
        try {
            // 첫 번째 IP에 대한 소켓 통신
            sendImageToServer(ip_data1);

            // 두 번째 IP에 대한 소켓 통신
            sendImageToServer(ip_data2);

        } catch (Exception e) {
            Log.e(StateSingleton.getInstance().TAG, "SocketThread runs into an error!");
        }
    }

    private void sendImageToServer(String ipAddress) {
        try {
            Socket clientSocket = new Socket(ipAddress, 1357);

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