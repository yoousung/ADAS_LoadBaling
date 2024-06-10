package com.example.demoproject_master;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView device1_ip;
    private TextView device2_ip;
    private TextView connectedDevices;
    private Button scanButton;
    private Button cameraButton;
    private Button exitButton;
    private LinearLayout linearLayout_Device1;
    private LinearLayout linearLayout_Device2;
    private Switch device_switch1;
    private Switch device_switch2;

    //private ArrayList<String> ip_list;
    private boolean device1_state; // Device1 선택 판정
    private boolean device2_state; // Device2 선택 판정
    private int case_index;
    private String ip_data;
    private CameraPreview cameraPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPermission();
        initViews();
        // 전송할 Device선택
        device_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                device1_state = isChecked;
            }
        });

        device_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                device2_state = isChecked;
            }
        });

        // 검색 버튼 클릭시 -> 현재 연결된 핸드폰 ip추출
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearArpCache();
                view_connect_device();
            }
        });

        // 카메라 버튼 클릭시 -> CameraPreview 클래스 시작
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_socket();

                if(case_index==0){
                    // 토스트 메시지 표시 예시
                    Toast.makeText(getApplicationContext(), "전송할 디바이스를 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    start_CameraPreviewActivity(case_index, ip_data);
                }

            }
        });

        // 재시작 버튼 클릭시 -> 앱 재시작
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 앱 종료
                finishAffinity();

                // 앱 재시작
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    // View초기설정
    private void initViews(){
        device1_ip = findViewById(R.id.connect_Device1);
        device2_ip = findViewById(R.id.connect_Device2);
        connectedDevices = findViewById(R.id.connectedDevices);
        scanButton = findViewById(R.id.scan_button);
        cameraButton = findViewById(R.id.camera_button);
        exitButton = findViewById(R.id.exit_button);
        linearLayout_Device1 = findViewById(R.id.linearlayout_device1);
        linearLayout_Device2 = findViewById(R.id.linearlayout_device2);
        device_switch1 = findViewById(R.id.device_switch1);
        device_switch2 = findViewById(R.id.device_switch2);
        linearLayout_Device1.setVisibility(View.GONE); // 뷰 숨기기
        linearLayout_Device2.setVisibility(View.GONE);
        device_switch1.setVisibility(View.GONE);
        device_switch2.setVisibility(View.GONE);
    }

    // 통신 설정 : 4가지 case, Device1 = on/off, Device2 = on/off
    private void set_socket(){
        // off, off
        if(!device1_state && !device2_state){
            Log.e("MainActivity", "Device를 선택해 주세요");
        }

        // on, off
        if(device1_state && !device2_state){
            case_index = 1;
        }

        // off, on
        if(!device1_state && device2_state){
            case_index = 2;
        }

        // on, on
        if(device1_state && device2_state){
            case_index = 3;
        }
    }

    // 카메라 프리뷰 시작
    private void start_CameraPreviewActivity(int caseIndex, String ipData) {
        Intent intent = new Intent(MainActivity.this, CameraPreview.class);
        intent.putExtra("case_index", caseIndex);
        intent.putExtra("ip_data", ipData);
        startActivity(intent);
    }


    // 핫스팟에 연결된 Device와 IP
    private void view_connect_device(){
        ArrayList<String> ip_list = new ArrayList<>();
        String ipList = getConnectedDevices();
        String[] list = ipList.split("\n"); // 추출한 ip분리
        ip_data = null;

        // 연결된 Device가 없을 때
        if (list.length <= 1) {
            connectedDevices.setText("No devices connected.");
            System.out.println("No devices connected.");
            linearLayout_Device1.setVisibility(View.INVISIBLE); // 뷰 숨기기
            linearLayout_Device2.setVisibility(View.INVISIBLE);
            device_switch1.setVisibility(View.INVISIBLE);
            device_switch2.setVisibility(View.INVISIBLE);
        } 
        // 연결된 Device가 1개 이상일 때
        else 
            {
            connectedDevices.setText(ipList);
            String[] ip_list_buff;

            for(int index = 1; index < list.length; index++){
                ip_list_buff = list[index].split(" ");
                ip_list.add(ip_list_buff[3]);
            }

            switch (list.length){
                
                // Device가 1개 연결되었을 때
                case 2:
                    linearLayout_Device1.setVisibility(View.VISIBLE); // 뷰 숨기기
                    device_switch1.setVisibility(View.VISIBLE);
                    device1_ip.setText(ip_list.get(0));
                    ip_data = ip_list.get(0);

                    break;
                    
                // Device가 2개 연결되었을 때
                case 3:
                    linearLayout_Device1.setVisibility(View.VISIBLE); // 뷰 숨기기
                    linearLayout_Device2.setVisibility(View.VISIBLE);
                    device_switch1.setVisibility(View.VISIBLE);
                    device_switch2.setVisibility(View.VISIBLE);

                    device1_ip.setText(ip_list.get(0));
                    device2_ip.setText(ip_list.get(1));
                    ip_data = ip_list.get(0)+" "+ip_list.get(1);

                    break;
            }
        }
    }

    // TedPermission 권한 체크
    private void setPermission() {
        PermissionListener permission = new PermissionListener() {
            @Override
            public void onPermissionGranted() { // 설정해 놓은 위험권한들이 허용 되었을 경우
                Toast.makeText(MainActivity.this, "권한이 허용 되었습니다.",Toast.LENGTH_SHORT).show();
                Log.d("권한", "권한 허가 상태");
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) { // 설정해 놓은 위험권한들이 허용되지 않았을 경우우
                Toast.makeText(MainActivity.this, "권한이 거부 되었습니다.",Toast.LENGTH_SHORT).show();
                Log.e("권한", "권한 거부 상태");
            }
        };
        TedPermission.with(MainActivity.this)
                .setPermissionListener(permission)
                .setRationaleMessage("위치정보와 카메라 권한이 필요합니다.")
                .setDeniedMessage("권한을 거부하시면 해당 기능을 사용할 수 없습니다. 설정에서 권한을 허용해주세요.")
                // 어느 권한을 허용할 것인지 설정
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    // 핫스팟으로 연결된 핸드폰의 IP주소
    private String getConnectedDevices() {
        StringBuilder ipList = new StringBuilder();

        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            InetAddress ipAddress = intToInetAddress(dhcpInfo.ipAddress);

            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" +");

                // 포맷 확인
                if (parts.length < 4) {
                    continue;
                }

                // 필요한 정보: IP 주소와 상태
                String ip = parts[0];
                String deviceStatus = parts[3];

                // 현재 호스트 디바이스 IP 확인
                if (ip.equalsIgnoreCase(ipAddress.getHostAddress())) {
                    continue;
                }

                if (!deviceStatus.equalsIgnoreCase("0x0")) {
                    ipList.append("Connected Device IP: ").append(ip).append("\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipList.toString();
    }
    private InetAddress intToInetAddress(int hostAddress) {
        byte[] addressBytes = {(byte) (0xff & hostAddress), (byte) (0xff & (hostAddress >> 8)), (byte) (0xff & (hostAddress >> 16)), (byte) (0xff & (hostAddress >> 24))};

        try {
            return InetAddress.getByAddress(addressBytes);
        } catch (Exception e) {
            throw new AssertionError();
        }
    }
    public void clearArpCache() {
        try {
            // ARP 테이블을 지우는 명령어를 실행
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("ip -s -s neigh flush all");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
