package com.example.demoproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.TextureView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendDataTask {

    private Context context;
    private String TAG = "SendTask";
    public static final String master_IP = "192.168.43.1";
    public static final int PORT[] = {13579, 2468}; // 결과값 송신을 위한 포트

    public SendDataTask(Context context) {
        this.context = context;
    }

    // state = 0 -> 핸드폰 연결X // state = 1 -> 핸드폰 연결O
    public static void sendBboxData(int port_index, int state, String bboxdata) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket clientSocket = new Socket(master_IP, PORT[port_index]);
                    BufferedOutputStream outToServer = new BufferedOutputStream(clientSocket.getOutputStream());

                    byte[] byteArray = bboxdata.getBytes();

                    outToServer.write(byteArray);
                    outToServer.flush();
                    clientSocket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    Log.e("SendData", "Error sending data: " + e.getMessage());
                }

            }
        }).start();
    }
}
