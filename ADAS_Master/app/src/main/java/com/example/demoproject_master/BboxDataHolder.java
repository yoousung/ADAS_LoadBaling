package com.example.demoproject_master;

// 스레드 간 데이터 전달을 위한 클래스
public class BboxDataHolder {
    private static BboxDataHolder instance;
    private String bboxdata;

    private BboxDataHolder() {
        // Private constructor to prevent instantiation
    }

    public static synchronized BboxDataHolder getInstance() {
        if (instance == null) {
            instance = new BboxDataHolder();
        }
        return instance;
    }

    public String getBboxdata() {
        return bboxdata;
    }

    public void setBboxdata(String bboxdata) {
        this.bboxdata = bboxdata;
    }
}
