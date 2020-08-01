package com.app.dto;

import java.io.InputStream;

public class ImageHolder {

    private String imageName;

    private InputStream imagePath;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImagePath() {
        return imagePath;
    }

    public void setImagePath(InputStream imagePath) {
        this.imagePath = imagePath;
    }
}
