package com.example.demoproject_master;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// SEG 소켓 통신
// Device2 socket (SEG)
public class ServerThread2 implements Runnable {

    private Handler uiHandler;
    private TextView device2_state;
    private int serverPort = 2468;
    public static final int MESSAGE_SEG_DATA = 2;

    private ImageView bdbox;

    public ServerThread2(Handler uiHandler, TextView device2_state, ImageView bdbox){
        this.uiHandler = uiHandler;
        this.device2_state = device2_state;
        this.bdbox = bdbox;
    }

    @Override
    public void run(){
        try {
            // 서버 소켓 생성
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Log.i("ServerThread", "Server listening on port " + serverPort);

            while (true) {
                // 클라이언트의 연결을 기다림
                Socket clientSocket = serverSocket.accept();
                Log.i("ServerThread", "Client connected: " + clientSocket.getInetAddress());

                // 클라이언트로부터 문자열 데이터 수신신
                BufferedInputStream inFromClient = new BufferedInputStream(clientSocket.getInputStream());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[4096]; // 1024 = 1KB 크기 버퍼
                int bytesRead;

                while ((bytesRead = inFromClient.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, bytesRead);
                }
                byte[] receivedData = byteArrayOutputStream.toByteArray();
                Bitmap receiveBitmap = BitmapFactory.decodeByteArray(receivedData, 0, receivedData.length);

                // UI 업데이트를 메인 스레드에서 처리
                uiHandler.post(() -> {
                    if (receiveBitmap != null){
                        device2_state.setText("on");
                    } else {
                        device2_state.setText("off");
                    }
                });

                // 메인 액티비티로 Bitmap 전달
                Message message = uiHandler.obtainMessage(MESSAGE_SEG_DATA, receiveBitmap);
                uiHandler.sendMessage(message);

//                if (receiveBitmap != null) {
//                    // 메인 액티비티로 Bitmap 전달
//                    Message message = uiHandler.obtainMessage(MESSAGE_SEG_DATA, receiveBitmap);
//                    uiHandler.sendMessage(message);
//                }

                // 클라이언트 소켓 닫기
                clientSocket.close();

                //Log.e("SEG", "Received!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
