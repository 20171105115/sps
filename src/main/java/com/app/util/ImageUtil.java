package com.app.util;

import com.app.dto.ImageHolder;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static final SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random r = new Random();

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        String relativePath = targetAddr + realFileName + extension;
        System.out.println(relativePath);
        File dest = new File(PathUtil.getImgBasePath() + relativePath);
        System.out.println(dest);
        try {
            Thumbnails.of(thumbnail.getImagePath()).size(200,200)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("图片创建失败"+e.getMessage());
        }
        return relativePath;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String imageName) {
        return imageName.substring(imageName.lastIndexOf("."));
    }

    private static String getRandomFileName() {
        int randomNum = r.nextInt(10000) + 89999;
        String nowTimeStr = simple.format(new Date());
        return randomNum + nowTimeStr;
    }

    public static void main(String[] args) {
        try {
            Thumbnails.of(new File("/Users/a20171105115/Documents/壁纸/2.jpg")).size(200,200)
                    .outputQuality(0.8f).toFile("/Users/a20171105115/Desktop/1.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理详情图，尺寸大一点
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateBigThumbnail(ImageHolder thumbnail, String targetAddr){
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        String relativePath = targetAddr + realFileName + extension;
        System.out.println(relativePath);
        File dest = new File(PathUtil.getImgBasePath() + relativePath);
        System.out.println(dest);
        try {
            Thumbnails.of(thumbnail.getImagePath()).size(337,640)
                    .outputQuality(0.8f).toFile(dest);

        } catch (IOException e) {
            throw new RuntimeException("图片创建失败"+e.getMessage());
        }
        return relativePath;
    }

    /**
     * storePath是文件的路径还是目录的路径， 如果storePath是文件路径则删除该文件，
     * 如果storePath是目录路径则删除该目录下的所有文件
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
