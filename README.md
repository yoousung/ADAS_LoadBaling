## Load Balancing
#### -Master : Black Box (4G ADAS)
#### -Receiver : Smart Phone (Galaxy S7, S8, S22)

### 1) Device
| |4G ADAS |Galaxy S7,S8,S22 |
|---|---|---|
|device |![image](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/0a86d5dd-1df3-4ac1-bf48-fda68e9509d7) |![image](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/3b21ea4a-93b5-46ae-b9ab-8b4897358308) |

### 2) Setting
- Gradle JDK jbr-17
- CMake 3.10.2
- [opencv](https://github.com/nihui/opencv-mobile/releases) (opencv-mobile-4.8.0-android)
- [ncnn](https://github.com/Tencent/ncnn/releases) (ncnn-20230816-android)

### 3) Diagram
![프레젠테이션1](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/1d9198a9-2aae-4e9b-925b-92a09547e4f5)



### 4) Model
|Tech |Model |Model Name |
|---|---|---|
|Default|ncnn |nanodet |
|Object Detection |ncnn |nanodet-m |
|Line Detection |yolo |yolov8_both |
|Segmentation |yolo |yolov8_both |


### 5) Result

|Tech |Before Image |After Image |
|---|---|---|
|Object Detection |![000017](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/7f2c15ff-ce2a-4ca9-a1eb-b8290079f6ec) | |
|Line Detection |![000017](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/7f2c15ff-ce2a-4ca9-a1eb-b8290079f6ec) |![result_ll](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/856c51f4-56e2-4095-be00-3aa170fab184) |
|Segmentation |![000017](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/ed708eb2-88aa-4ddd-92a0-68b33677a6dc) |![result_da](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/91e1b2a6-3e62-4163-9bcf-ad286f4e1793) |
|Both |![000017](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/481f0463-6a75-4aef-9793-db77ff002a36) |![result_both](https://github.com/yoousung/ADAS_LoadBaling/assets/108113584/975f2eef-bd48-4c4b-ad17-c45399daa13b) |


| |Device |
|---|---|
|4G ADAS | ![IMG_0070](https://github.com/bert13069598/LoadBalancing/assets/89738612/c254fe30-5345-43aa-9277-acbd2141e1b6) |
|Galaxy S22 |![Screenshot_20231222_005406_DemoProject](https://github.com/bert13069598/LoadBalancing/assets/89738612/b11a6f93-9214-4b89-96e1-dba3c98910c2)|




### 6) reference
https://github.com/Digital2Slave/ncnn-android-yolov8-seg/wiki/Convert-yolov8-model-to-ncnn-model
https://github.com/RangiLyu/nanodet  
https://github.com/ultralytics/ultralytics  
https://github.com/nihui/ncnn-android-nanodet  
https://github.com/FeiGeChuanShu/yolov5-seg-ncnn  
