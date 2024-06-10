#ifndef YOLO_H
#define YOLO_H

#include <opencv2/core/core.hpp>

#include <net.h>

struct Yolov8Object
{
    cv::Rect_<float> rect;
    int label;
    float prob;
    std::vector<float> mask_feat;
    cv::Mat cv_mask;

};
struct GridAndStride
{
    int grid0;
    int grid1;
    int stride;
};
class Yolov8
{
public:
    Yolov8();

    int load(AAssetManager* mgr, bool use_gpu = false);

    int detect(const cv::Mat& rgb, cv::Mat mask, float prob_threshold = 0.35f, float nms_threshold = 0.45f);

    int draw(cv::Mat& rgb, cv::Mat mask);

private:

    ncnn::Net yolov8;

    int target_size=320;

    ncnn::UnlockedPoolAllocator blob_pool_allocator;
    ncnn::PoolAllocator workspace_pool_allocator;
};

#endif // YOLO_H
