package com.pviksy.cycalfx.data;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.List;

public class ImageCache {
    private HashMap<String, Image> cache;

    public ImageCache() {
        cache = new HashMap<>();
    }

    public Image getImage(String url) {
        Image image = cache.get(url);
        if (image == null) {
            image = new Image(url, true); // true for background loading
            cache.put(url, image);
        }
        return image;
    }

    public void addAll(List<String> urls) {
        for (String url : urls) {
            getImage(url);
        }
    }
}
