## Load Balancing
기존 [블랙박스](http://itempage3.auction.co.kr/DetailView.aspx?ItemNo=C522794645&frm3=V2)에 AI 탑재 후 이기종 컴퓨팅의 자원 공유를 통한 저가형 ADAS 구현

|블랙박스(Master)|
|:---:|
|![IMG_0070](https://github.com/bert13069598/LoadBalancing/assets/89738612/c254fe30-5345-43aa-9277-acbd2141e1b6)|
|갤럭시 s22(Server)|
|![Screenshot_20231222_005406_DemoProject](https://github.com/bert13069598/LoadBalancing/assets/89738612/b11a6f93-9214-4b89-96e1-dba3c98910c2)|

### Setting
- Gradle JDK corretto-11
- CMake 3.22.1
- [opencv](https://github.com/nihui/opencv-mobile/releases)
- [ncnn](https://github.com/Tencent/ncnn/releases)

CMakeLists.txt
```cmake
project(nanodetncnn)

cmake_minimum_required(VERSION 3.22.1)

set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/opencv-mobile-X.X.X-android/sdk/native/jni)
find_package(OpenCV REQUIRED core imgproc)

set(ncnn_DIR ${CMAKE_SOURCE_DIR}/ncnn-20XXXXXX-android/${ANDROID_ABI}/lib/cmake/ncnn)
find_package(ncnn REQUIRED)

add_library(ncnntotal SHARED ncnn.cpp nanodet.cpp yolov8.cpp)
target_link_libraries(ncnntotal ncnn ${OpenCV_LIBS} mediandk jnigraphics)
```

### detection
nanodet

### segmentation
yolov8-seg

### reference
https://github.com/RangiLyu/nanodet  
https://github.com/ultralytics/ultralytics  
https://github.com/nihui/ncnn-android-nanodet  
https://github.com/FeiGeChuanShu/yolov5-seg-ncnn  
