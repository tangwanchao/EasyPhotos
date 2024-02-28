package com.huantansheng.easyphotos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.huantansheng.easyphotos.Builder.AlbumBuilder;
import com.huantansheng.easyphotos.engine.ImageEngine;
import com.huantansheng.easyphotos.models.ad.AdListener;
import com.huantansheng.easyphotos.models.album.AlbumModel;
import com.huantansheng.easyphotos.models.sticker.StickerModel;
import com.huantansheng.easyphotos.models.sticker.entity.TextStickerData;
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils;
import com.huantansheng.easyphotos.utils.bitmap.SaveBitmapCallBack;
import com.huantansheng.easyphotos.utils.media.MediaScannerConnectionUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * EasyPhotos的启动管理器
 * Created by huan on 2017/10/18.
 */
public class EasyPhotos {

    //easyPhotos的返回数据Key
    public static final String RESULT_PHOTOS = "keyOfEasyPhotosResult";
    public static final String RESULT_SELECTED_ORIGINAL = "keyOfEasyPhotosResultSelectedOriginal";

    /**
     * 预加载
     * 调不调用该方法都可以，不调用不影响EasyPhotos正常使用
     * 第一次扫描媒体库可能会慢，调用预加载会使真正打开相册的速度加快
     * 若调用该方法，建议自行判断代码书写位置，建议在用户打开相册的3秒前调用，比如app主页面或调用相册的上一页
     * 该方法如果没有授权读取权限的话，是无效的，所以外部加不加权限控制都可以，加的话保证执行，不加也不影响程序正常使用
     *
     * @param cxt 上下文
     */
    public static void preLoad(Context cxt) {
        AlbumModel.getInstance().query(cxt, null);
    }

    /**
     * 预加载
     * 调不调用该方法都可以，不调用不影响EasyPhotos正常使用
     * 第一次扫描媒体库可能会慢，调用预加载会使真正打开相册的速度加快
     * 若调用该方法，建议自行判断代码书写位置，建议在用户打开相册的3秒前调用，比如app主页面或调用相册的上一页
     * 该方法如果没有授权读取权限的话，是无效的，所以外部加不加权限控制都可以，加的话保证执行，不加也不影响程序正常使用
     *
     * @param cxt      上下文
     * @param callBack 预加载完成的回调，若进行UI操作，需自行切回主线程。
     */
    public static void preLoad(Context cxt, AlbumModel.CallBack callBack) {
        AlbumModel.getInstance().query(cxt, callBack);
    }


    /**
     * 创建相机
     *
     * @param activity 上下文
     * @param useWidth 是否使用宽高数据
     * @return AlbumBuilder
     */
    public static AlbumBuilder createCamera(Activity activity,
                                            boolean useWidth) {
        return AlbumBuilder.createCamera(activity).setUseWidth(useWidth);
    }

    public static AlbumBuilder createCamera(Fragment fragment,
                                            boolean useWidth) {
        return AlbumBuilder.createCamera(fragment).setUseWidth(useWidth);
    }

    public static AlbumBuilder createCamera(FragmentActivity activity,
                                            boolean useWidth) {
        return AlbumBuilder.createCamera(activity).setUseWidth(useWidth);
    }

    public static AlbumBuilder createCamera(androidx.fragment.app.Fragment fragmentV,
                                            boolean useWidth) {
        return AlbumBuilder.createCamera(fragmentV).setUseWidth(useWidth);
    }

    /**
     * 创建相册
     *
     * @param activity     上下文
     * @param isShowCamera 是否显示相机按钮
     * @param useWidth     是否使用宽高数据。
     *                     true：会保证宽高数据的正确性，返回速度慢，耗时，尤其在华为mate30上，可能点击完成后会加载三四秒才能返回。
     *                     false:有宽高数据但不保证正确性，点击完成后秒回，但可能有因旋转问题导致的宽高相反的情况，以及极少数的宽高为0情况。
     * @param imageEngine  图片加载引擎的具体实现
     * @return AlbumBuilder 建造者模式配置其他选项
     */
    public static AlbumBuilder createAlbum(Activity activity, boolean isShowCamera,
                                           boolean useWidth, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(activity, isShowCamera, imageEngine).setUseWidth(useWidth);
    }

    public static AlbumBuilder createAlbum(Fragment fragment, boolean isShowCamera,
                                           boolean useWidth, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(fragment, isShowCamera, imageEngine).setUseWidth(useWidth);
    }

    public static AlbumBuilder createAlbum(FragmentActivity activity, boolean isShowCamera,
                                           boolean useWidth, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(activity, isShowCamera, imageEngine).setUseWidth(useWidth);
    }

    public static AlbumBuilder createAlbum(androidx.fragment.app.Fragment fragmentV,
                                           boolean isShowCamera, boolean useWidth,
                                           @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(fragmentV, isShowCamera, imageEngine).setUseWidth(useWidth);
    }


//*********************AD************************************


    /**
     * 设置广告监听
     * 内部使用，无需关心
     *
     * @param adListener 广告监听
     */
    public static void setAdListener(AdListener adListener) {
        AlbumBuilder.setAdListener(adListener);
    }

    /**
     * 刷新图片列表广告数据
     */
    public static void notifyPhotosAdLoaded() {
        AlbumBuilder.notifyPhotosAdLoaded();
    }

    /**
     * 刷新专辑项目列表广告
     */
    public static void notifyAlbumItemsAdLoaded() {
        AlbumBuilder.notifyAlbumItemsAdLoaded();
    }


//*************************bitmap功能***********************************/

    /**
     * 回收bitmap
     *
     * @param bitmap 要回收的bitmap
     */
    public static void recycle(Bitmap bitmap) {
        BitmapUtils.recycle(bitmap);
    }

    /**
     * 回收bitmap数组中的所有图片
     *
     * @param bitmaps 要回收的bitmap数组
     */
    public static void recycle(Bitmap... bitmaps) {
        BitmapUtils.recycle(bitmaps);
    }

    /**
     * 回收bitmap集合中的所有图片
     *
     * @param bitmaps 要回收的bitmap集合
     */
    public static void recycle(List<Bitmap> bitmaps) {
        BitmapUtils.recycle(bitmaps);
    }

    /**
     * 给图片添加水印，水印会根据图片宽高自动缩放处理
     *
     * @param watermark     水印
     * @param image         添加水印的图片
     * @param srcImageWidth 水印对应的原图片宽度,即ui制作水印时参考的要添加水印的图片的宽度
     * @param offsetX       添加水印的X轴偏移量
     * @param offsetY       添加水印的Y轴偏移量
     * @param addInLeft     true 在左下角添加水印，false 在右下角添加水印
     * @param orientation   Bitmap的旋转角度。当useWidth为true时，Photo实体类中会有orientation，若bitmap
     *                      不是用户手机内图片，填0即可。
     * @return 添加水印后的bitmap
     */
    public static Bitmap addWatermark(Bitmap watermark, Bitmap image, int srcImageWidth,
                                      int offsetX, int offsetY, boolean addInLeft,
                                      int orientation) {
        return BitmapUtils.addWatermark(watermark, image, srcImageWidth, offsetX, offsetY,
                addInLeft, orientation);
    }

    /**
     * 给图片添加带文字和图片的水印，水印会根据图片宽高自动缩放处理
     *
     * @param watermark     水印图片
     * @param image         要加水印的图片
     * @param srcImageWidth 水印对应的原图片宽度,即ui制作水印时参考的要添加水印的图片的宽度
     * @param text          要添加的文字
     * @param offsetX       添加水印的X轴偏移量
     * @param offsetY       添加水印的Y轴偏移量
     * @param addInLeft     true 在左下角添加水印，false 在右下角添加水印
     * @param orientation   Bitmap的旋转角度。当useWidth为true时，Photo实体类中会有orientation，若bitmap
     *                      不是用户手机内图片，填0即可。
     * @return 添加水印后的bitmap
     */
    public static Bitmap addWatermarkWithText(Bitmap watermark, Bitmap image, int srcImageWidth,
                                              @NonNull String text, int offsetX, int offsetY,
                                              boolean addInLeft, int orientation) {
        return BitmapUtils.addWatermarkWithText(watermark, image, srcImageWidth, text, offsetX,
                offsetY,
                addInLeft, orientation);
    }

    /**
     * 保存Bitmap到指定文件夹
     *
     * @param act         上下文
     * @param dirPath     文件夹全路径
     * @param bitmap      bitmap
     * @param namePrefix  保存文件的前缀名，文件最终名称格式为：前缀名+自动生成的唯一数字字符+.png
     * @param notifyMedia 是否更新到媒体库
     * @param callBack    保存图片后的回调，回调已经处于UI线程
     */
    public static void saveBitmapToDir(Activity act, String dirPath, String namePrefix,
                                       Bitmap bitmap, boolean notifyMedia,
                                       SaveBitmapCallBack callBack) {
        BitmapUtils.saveBitmapToDir(act, dirPath, namePrefix, bitmap, notifyMedia, callBack);
    }


    /**
     * 把View画成Bitmap
     *
     * @param view 要处理的View
     * @return Bitmap
     */
    public static Bitmap createBitmapFromView(View view) {
        return BitmapUtils.createBitmapFromView(view);
    }

    //**************更新媒体库***********************

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt       上下文
     * @param filePaths 更新的文件地址
     */
    public static void notifyMedia(Context cxt, String... filePaths) {
        MediaScannerConnectionUtils.refresh(cxt, filePaths);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt   上下文
     * @param files 更新的文件
     */
    public static void notifyMedia(Context cxt, File... files) {
        MediaScannerConnectionUtils.refresh(cxt, files);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt      上下文
     * @param fileList 更新的文件地址集合
     */
    public static void notifyMedia(Context cxt, List<String> fileList) {
        MediaScannerConnectionUtils.refresh(cxt, fileList);
    }


    //*********************************贴纸***************************


    /**
     * 添加文字贴纸的文字数据
     *
     * @param textStickerData 文字贴纸的文字数据
     */
    public static void addTextStickerData(TextStickerData... textStickerData) {
        StickerModel.textDataList.addAll(Arrays.asList(textStickerData));
    }

    /**
     * 清空文字贴纸的数据
     */
    public static void clearTextStickerDataList() {
        StickerModel.textDataList.clear();
    }
}
