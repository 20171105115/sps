package com.app.util;

import java.util.Date;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/projectdev/image";
        } else {
            basePath = "/Users/a20171105115/image/sps";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getImagePath(long userId) {
        String imagePath = "/upload/images/user/" + userId + "/";
        return imagePath.replace("/", separator);
    }


    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
